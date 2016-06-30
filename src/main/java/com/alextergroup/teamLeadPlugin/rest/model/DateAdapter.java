package com.alextergroup.teamLeadPlugin.rest.model;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateAdapter  extends XmlAdapter<String, Date>{

    private static final SimpleDateFormat dateFmt = new SimpleDateFormat("dd.mm.yyyy");

    @Override
    public Date unmarshal(String v) throws Exception {
        synchronized (dateFmt){
            return dateFmt.parse(v);
        }
    }

    @Override
    public String marshal(Date v) throws Exception {
        synchronized (dateFmt){
            return dateFmt.format(v);
        }
    }
}
