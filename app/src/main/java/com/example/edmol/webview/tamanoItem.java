package com.example.edmol.webview;

/**
 * Created by Fam. Beltrán on 21/11/2018.
 */

public class tamanoItem {
    private String mTamanoNombre;
        private int mTamanoImg;

        public tamanoItem(String tamanoNombre, int tamanoImg){
            mTamanoNombre = tamanoNombre;
            mTamanoImg = tamanoImg;
        }

        public String getTamanoNombres(){
            return mTamanoNombre;
        }

        public int getTamanoImg(){
            return mTamanoImg;
        }
}