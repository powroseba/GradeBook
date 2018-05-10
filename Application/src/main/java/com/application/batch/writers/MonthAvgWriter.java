package com.application.batch.writers;

import com.domain.batch.MonthStudentAvg;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

public class MonthAvgWriter implements ItemWriter<List<MonthStudentAvg>> {
    @Override
    public void write(List<? extends List<MonthStudentAvg>> list) throws Exception {

    }
}
