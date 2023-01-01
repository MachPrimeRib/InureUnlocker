package app.simple.inureunlocker.extensions.popup;

import android.content.Context;
import android.widget.LinearLayout;

import androidx.core.content.ContextCompat;
import app.simple.inureunlocker.R;

public class PopupLinearLayout extends LinearLayout {
    public PopupLinearLayout(Context context) {
        super(context);
        init();
    }
    
    public PopupLinearLayout(Context context, int orientation) {
        super(context);
        init(orientation);
    }
    
    private void init() {
        //        int p = getResources().getDimensionPixelOffset(R.dimen.popup_padding);
        //        setPadding(p, p, p, p);
        setOrientation(LinearLayout.VERTICAL);
    }
    
    private void init(int orientation) {
        //        int p = getResources().getDimensionPixelOffset(R.dimen.popup_padding);
        //        setPadding(p, p, p, p);
        setOrientation(orientation);
        setBackground(ContextCompat.getDrawable(getContext(), R.drawable.bg_popup));
    }
}
