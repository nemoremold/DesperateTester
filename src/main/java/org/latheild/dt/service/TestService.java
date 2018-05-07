package org.latheild.dt.service;

import java.util.List;

public interface TestService {

    String test();

    String test2();

    String test3();

    List<List> uploadSourceFile(String filePath, String className);

    Object testSingleCase(String filePath, String className, String[] methodDesc, Object[] objects);

    List<List<List>> testBatchCases(String filePath, String className, String[] methodDesc, String xlsPath);

}
