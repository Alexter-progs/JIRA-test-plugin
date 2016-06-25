package com.alextergroup.teamLeadPlugin.rest.model;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateAdapter  extends XmlAdapter<String, Date>{

    private static final SimpleDateFormat dateTimeFmt = new SimpleDateFormat("dd.MM.yy HH:mm");


    @Override
    public Date unmarshal(String v) throws Exception {
        synchronized (dateTimeFmt){
            return dateTimeFmt.parse(v);
        }
    }

    @Override
    public String marshal(Date v) throws Exception {
        synchronized (dateTimeFmt){
            return dateTimeFmt.format(v);
        }
    }
}
