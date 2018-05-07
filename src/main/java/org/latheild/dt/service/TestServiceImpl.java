package org.latheild.dt.service;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import org.latheild.dt.utils.ClassLoaderUtil;
import org.latheild.dt.utils.ExcelUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl implements TestService {

    private static Logger logger = LoggerFactory.getLogger(TestService.class);

    @Override
    public String test() {
        ArrayList<String> ops = new ArrayList<>();
        ops.add("-Xlint:unchecked");
        Class<?> cls = ClassLoaderUtil.getInstance().loadClass(
            ops,
            "/Users/nemoremold/Documents/Projects/Project DT/src/main/java/org/latheild/dt/testUnit/ComputerSalesTestUnit.java", 
            "org.latheild.dt.testUnit.ComputerSalesTestUnit"
        );

        StringBuilder sb = new StringBuilder();
        sb.append(cls.getName());
        sb.append("<br/>");
        Method[] methods = cls.getDeclaredMethods();
        for (Method method : methods) {
            sb.append(method.getName());
            sb.append("(");
            Class<?>[] parameterTypes = method.getParameterTypes();
            for (int i = 0; i < parameterTypes.length; ++i) {
                sb.append(parameterTypes[i].getSimpleName());
                if (i < parameterTypes.length - 1) {
                    sb.append(", ");
                }
            }
            sb.append("): ");
            sb.append(Modifier.toString(method.getModifiers()));
            sb.append(" ");
            sb.append(method.getReturnType().getSimpleName());
            sb.append("<br/>");
        }
        try {
            System.out.println(
                    ClassLoaderUtil.getInstance().invoke(
                            cls,
                            "getCommission",
                            new Class[]{String.class, String.class, String.class},
                            new Object[]{"90", "12", "10"}
                    )
            );
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        if (logger.isDebugEnabled()) {
            logger.debug(cls.toString());
        }
        return sb.toString();
    }

    @Override
    public String test2() {
        List<List<List>> list = ExcelUtil.getInstance().readExcel(
                "/Users/nemoremold/Documents/Projects/Project DT/src/main/java/org/latheild/dt/testUnit/test.xlsx"
        );
        int cntSheets = 0;
        for (List<List> sheet : list) {
            System.out.println("For sheet: " + (cntSheets++));
            int cntRows = 0;
            for (List row : sheet) {
                System.out.print("For row: " + (cntRows++) + " ");
                System.out.println(row.toString());
            }
        }

        return list.toString();
    }

    @Override
    public String test3() {
        ArrayList<String> ops = new ArrayList<>();
        ops.add("-Xlint:unchecked");
        Class<?> cls = ClassLoaderUtil.getInstance().loadClass(
                ops,
                "/Users/nemoremold/Documents/Projects/Project DT/src/main/java/org/latheild/dt/testUnit/ComputerSalesTestUnit.java",
                "org.latheild.dt.testUnit.ComputerSalesTestUnit"
        );

        List<List<List>> list = ExcelUtil.getInstance().readExcel(
                "/Users/nemoremold/Documents/Projects/Project DT/src/main/java/org/latheild/dt/testUnit/test2.xlsx"
        );

        int cntSheets = 0;
        for (List<List> sheet : list) {
            System.out.println("For sheet: " + (cntSheets++));
            int cntRows = 0;
            for (List row : sheet) {
                System.out.print("For row: " + (cntRows++) + " ");
                System.out.println(row.toString());
                if (cntRows == 1) {
                    continue;
                }
                int size = row.size();
                Object[] objects = new Object[size];
                for (int i = 0; i < (3 > size ? size : 3); ++i) {
                    objects[i] = row.get(i).toString();
                }
                try {
                    System.out.print(
                            ClassLoaderUtil.getInstance().invoke(
                                    cls,
                                    "getCommission",
                                    new Class[]{String.class, String.class, String.class},
                                    objects
                            )
                    );
                } catch (Exception e) {
                    logger.error(e.getMessage());
                }
            }
        }

        return "";
    }

    @Override
    public List<List> uploadSourceFile(String filePath, String className) {
        ArrayList<String> ops = new ArrayList<>();
        ops.add("-Xlint:unchecked");
        Class<?> cls = ClassLoaderUtil.getInstance().loadClass(
                ops,
                filePath,
                className
        );

        List<List> methodLists = new ArrayList<>();
        Method[] methods = cls.getDeclaredMethods();
        for (Method method : methods) {
            List<String> methodList = new ArrayList<>();
            methodList.add(method.getName());
            Class<?>[] parameterTypes = method.getParameterTypes();
            for (Class<?> parameterType : parameterTypes) {
                methodList.add(parameterType.getSimpleName());
            }
            methodList.add(Modifier.toString(method.getModifiers()));
            methodList.add(method.getReturnType().getSimpleName());
            methodLists.add(methodList);
        }

        if (logger.isDebugEnabled()) {
            logger.debug(cls.toString());
        }
        return methodLists;
    }

    @Override
    public Object testSingleCase(String filePath, String className, String[] methodDesc, Object[] objects) {
        ArrayList<String> ops = new ArrayList<>();
        ops.add("-Xlint:unchecked");
        Class<?> cls = ClassLoaderUtil.getInstance().loadClass(
                ops,
                filePath,
                className
        );

        Method[] methods = cls.getDeclaredMethods();
        for (Method method : methods) {
            int cnt = 0;
            if (!method.getName().equals(methodDesc[cnt++])) {
                continue;
            }
            Class<?>[] parameterTypes = method.getParameterTypes();
            for (Class<?> parameterType : parameterTypes) {
                if (!parameterType.getSimpleName().equals(methodDesc[cnt++])) {
                    break;
                }
            }
            if (!Modifier.toString(method.getModifiers()).equals(methodDesc[cnt++])) {
                continue;
            }
            if (!method.getReturnType().getSimpleName().equals(methodDesc[cnt])) {
                continue;
            }

            try {
                Object[] objs = new Object[cnt - 2];
                System.arraycopy(objects, 0, objs, 0, cnt - 2);
                Object result = ClassLoaderUtil.getInstance().invoke(
                        cls,
                        method.getName(),
                        parameterTypes,
                        objs
                );
                System.out.println(objects.length + " " + (cnt - 2));
                if (objects.length > cnt - 2) {
                    if ((result.getClass().getName().equals(Double.class.getName())
                            || result.getClass().getName().equals(double.class.getName()))
                            && (Double) result == ((Double) result).intValue()) {
                        return (Integer.valueOf(((Double) result).intValue()).toString().equals(objects[cnt - 2].toString()) ? "SUCCESS" : result);
                    }
                    return (result.toString().equals(objects[cnt - 2].toString()) ? "SUCCESS" : result);
                } else {
                    return result;
                }
            } catch (Exception e) {
                if (objects.length > cnt - 2) {
                    return (e.getMessage().equals(objects[cnt - 2].toString()) ? "SUCCESS" : e.getMessage());
                } else {
                    return e.getMessage();
                }
            }
        }
        return null;
    }

    @Override
    public List<List<List>> testBatchCases(String filePath, String className, String[] methodDesc, String xlsPath) {
        ArrayList<String> ops = new ArrayList<>();
        ops.add("-Xlint:unchecked");
        Class<?> cls = ClassLoaderUtil.getInstance().loadClass(
                ops,
                filePath,
                className
        );

        List<List<List>> list = ExcelUtil.getInstance().readExcel(
                xlsPath
        );

        Method[] methods = cls.getDeclaredMethods();
        for (Method method : methods) {
            int cnt = 0;
            if (!method.getName().equals(methodDesc[cnt++])) {
                continue;
            }
            Class<?>[] parameterTypes = method.getParameterTypes();
            for (Class<?> parameterType : parameterTypes) {
                if (!parameterType.getSimpleName().equals(methodDesc[cnt++])) {
                    break;
                }
            }
            if (!Modifier.toString(method.getModifiers()).equals(methodDesc[cnt++])) {
                continue;
            }
            if (!method.getReturnType().getSimpleName().equals(methodDesc[cnt])) {
                continue;
            }

            int cntSheets = 0;
            for (List<List> sheet : list) {
                System.out.println("For sheet: " + (cntSheets++));
                int cntRows = 0;
                for (List row : sheet) {
                    System.out.print("For row: " + (cntRows++) + " ");
                    System.out.println(row.toString());
                    if (cntRows == 1) {
                        continue;
                    }
                    int size = row.size();
                    Object[] objects = new Object[cnt - 2];
                    for (int i = 0; i < (cnt - 2 > size ? size : cnt - 2); ++i) {
                        if (parameterTypes[i].getName().equals(String.class.getName())) {
                            objects[i] = row.get(i).toString();
                        }
                        else {
                            objects[i] = row.get(i);
                        }
                    }

                    Object result;
                    try {
                        result = ClassLoaderUtil.getInstance().invoke(
                                cls,
                                method.getName(),
                                parameterTypes,
                                objects
                        );
                        if (row.size() > cnt - 2) {
                            if ((result.getClass().getName().equals(Double.class.getName())
                                    || result.getClass().getName().equals(double.class.getName()))
                                    && (Double) result == ((Double) result).intValue()) {
                                row.add(Integer.valueOf(((Double) result).intValue()).toString().equals(row.get(cnt - 2).toString()) ? "SUCCESS" : result);
                            } else {
                                row.add((result.toString().equals(row.get(cnt - 2).toString())) ? "SUCCESS" : result);
                            }
                        } else {
                            row.add(result);
                        }
                    } catch (Exception e) {
                        if (row.size() > cnt - 2) {
                            row.add((e.getMessage().equals(row.get(cnt - 2).toString())) ? "SUCCESS" : e.getMessage());
                        } else {
                            row.add(e.getMessage());
                        }
                    }
                }
            }
            return list;
        }
        return null;
    }

}
