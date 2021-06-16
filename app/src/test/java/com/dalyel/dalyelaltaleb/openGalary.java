package com.dalyel.dalyelaltaleb;

import android.content.Context;
import android.content.Intent;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static junit.framework.TestCase.assertEquals;


public class openGalary {
    @Mock
    private Context mContext;

    @Mock
    private Intent androidIntent;
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);


    }
    @Test
    public void openGalary(){

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");

        assertEquals(false, intent);
    }


}
