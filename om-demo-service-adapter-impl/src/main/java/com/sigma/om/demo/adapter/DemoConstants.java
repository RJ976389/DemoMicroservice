package com.sigma.om.demo.adapter;

public class DemoConstants
{
    private DemoConstants()
    {

    }

    private static DemoConstants demoConstants =null;

    public static DemoConstants test() {
        if(demoConstants !=null)
        {
            demoConstants=new DemoConstants();
        }
        return demoConstants;
    }
    public static final String Mobile_Voice_CFSS="Mobile Voice CFSS";
    public static final String Mobile_Voice_CFSS_GUID="1af62c90-213c-4a89-8985-b2083cf692c1";
    public static final String Phone_Number="Phone Number";
    public static final String Mobile_Voice_Identity_RFS="Mobile Voice Identity RFS";
    public static final String Mobile_Voice_Identity_RFS_GUID="3ea24006-0832-4b15-8213-3ac0c2aa0b78";
    public static final String Home_Mobile_Network="Home Mobile Network";
}
