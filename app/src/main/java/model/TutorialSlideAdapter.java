package model;

import android.content.Context;
import android.content.res.TypedArray;
import android.speech.tts.TextToSpeech;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import fr.lesage.olivier.info503.R;

public class TutorialSlideAdapter extends PagerAdapter {
    private Context context;
    private LayoutInflater inflater;

    private String title[];
    private String text[];
    private int imageID;

    public TutorialSlideAdapter(Context context, LayoutInflater inflater, int nbSlide, int titleID, int textID, int imageID) {
        this.context = context;
        this.inflater = inflater;

        this.title = context.getResources().getStringArray(titleID);
        this.text = context.getResources().getStringArray(textID);
        this.imageID = imageID;
    }

    @Override
    public int getCount() {
        return this.title.length;
    }

    @Override
    public boolean isViewFromObject( View view, Object o) {
        return view == o;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = this.inflater.inflate(R.layout.turorial_slide, container, false);

        TextView title = (TextView) view.findViewById(R.id.tutorial_title);
        title.setText(this.title[position]);

        TextView text = (TextView) view.findViewById(R.id.tutorial_message);
        text.setText(this.text[position]);

        TypedArray img = this.context.getResources().obtainTypedArray(this.imageID);
        ImageView image = (ImageView) view.findViewById(R.id.tutorial_image);
        image.setImageDrawable(ContextCompat.getDrawable(this.context, img.getResourceId(position, -1)));
        img.recycle();

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
