package com.sd.utils;

import com.sd.clock.EventsClockImpl;
import com.sd.statistic.EventsStatisticImpl;
import com.sd.statistic.EventsStatistic;

import java.io.PrintWriter;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        EventsStatistic statistic = new EventsStatisticImpl(
                new EventsClockImpl(), out
        );
        boolean flag = true;
        while (flag) {
            String next = in.next();
            switch (next) {
                case "getAll":
                    out.write(
                            statistic.getAllEventStatistic().getIntValue()
                    );
                    break;
                case "printAll":
                    statistic.printStatistic();
                    break;
                case "exit":
                    flag = false;
                    break;
                default:
                    if (next.startsWith("statisticByName")) {
                        out.write(
                                statistic.getEventStatisticByName(
                                        in.next()).getIntValue()
                        );
                    } else {
                        statistic.incEvent(next);
                    }
            }
            out.println();
            out.flush();
        }
    }

}
