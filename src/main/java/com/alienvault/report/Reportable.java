package com.alienvault.report;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface Reportable {
    String generateReport(List<String> parameters) throws Exception;
}
