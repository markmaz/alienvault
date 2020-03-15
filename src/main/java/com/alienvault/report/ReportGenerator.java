package com.alienvault.report;

import java.util.List;

public class ReportGenerator {
    private Reportable reportable;

    public ReportGenerator(Reportable reportable){
        this.reportable = reportable;
    }

    public String getReport(List<String> parameters) throws Exception {
        return reportable.generateReport(parameters);
    }
}
