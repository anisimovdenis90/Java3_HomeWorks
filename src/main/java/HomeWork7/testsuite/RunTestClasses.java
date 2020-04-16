package HomeWork7.testsuite;

import HomeWork7.testsuite.annotations.AfterSuite;
import HomeWork7.testsuite.annotations.BeforeSuite;
import HomeWork7.testsuite.annotations.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class RunTestClasses {

    private final static Map<Method, Integer> methodsToStart = new HashMap<>();

    public static void start(Class<?> testClass) {
        testClassProcessing(testClass);
    }

    public static void start(String testClassName) {
        try {
            testClassProcessing(Class.forName(testClassName));
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("Неверное имя класса!");
        }
    }

    private static void testClassProcessing(Class<?> testClass) {
        if (methodsToStart.size() != 0) {
            methodsToStart.clear();
        }

        for (Method method : testClass.getMethods()) {
            if (method.isAnnotationPresent(BeforeSuite.class)) {
                uniqAnnotationTestProcessing("BeforeSuite", method, Integer.MAX_VALUE);
            }
            if (method.isAnnotationPresent(AfterSuite.class)) {
                uniqAnnotationTestProcessing("AfterSuite", method, Integer.MIN_VALUE);
            }
            if (method.isAnnotationPresent(Test.class)) {
                methodsToStart.put(method, method.getAnnotation(Test.class).priority().getIntPriority());
            }
        }

        try {
            sotrByPriorityAndStartMethods(testClass.getConstructor().newInstance());
        } catch (InvocationTargetException | IllegalAccessException | InstantiationException | NoSuchMethodException e) {
            throw new IllegalArgumentException("Невозможно создать экземпляр тестового класса!");
        }
    }

    private static void uniqAnnotationTestProcessing(String annotationName, Method method, int priority) {
        if (methodsToStart.containsValue(priority)) {
            throw new RuntimeException(String.format("Множественное использование аннотации %s!", annotationName));
        } else {
            methodsToStart.put(method, priority);
        }
    }

    private static <T> void sotrByPriorityAndStartMethods(T instanceOfTestClass) throws InvocationTargetException, IllegalAccessException {
        final List<Map.Entry<Method, Integer>> sortedByPriorityMethods = new ArrayList<>(methodsToStart.entrySet());
        sortedByPriorityMethods.sort((o1, o2) -> o2.getValue().compareTo(o1.getValue()));

         for (Map.Entry<Method, Integer> sortedMethod : sortedByPriorityMethods) {
            sortedMethod.getKey().invoke(instanceOfTestClass);
         }
    }
}
