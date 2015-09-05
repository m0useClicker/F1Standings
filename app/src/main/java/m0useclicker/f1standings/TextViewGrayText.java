package m0useclicker.f1standings;

import android.content.Context;
import android.graphics.Color;
import android.widget.TextView;

/**
 * TextView with predefined text color set to GRAY
 */
class TextViewGrayText extends TextView {

    public TextViewGrayText(Context context) {
        super(context);
        setTextColor(Color.GRAY);
    }
}
