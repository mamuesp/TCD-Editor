package com.mykong.common;

import java.sql.Timestamp;

public class GetCurrentTimeStamp
{
    public static void main( String[] args )
    {
        java.util.Date date= new java.util.Date();
        System.out.println(new Timestamp(date.getTime()));
    }
}
