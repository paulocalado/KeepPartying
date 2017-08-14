package com.codgin.paulo.keeppartying.Service;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by Paulo on 28/07/2017.
 */

public class PicassoService {

    public static void downloadImage(Context context, String url, ImageView img){
        if(url!= null && url.length()>0 ){
            Picasso.with(context).load(url).into(img);
        }

    }
}
