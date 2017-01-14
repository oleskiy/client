package weartest.com.client.zxing;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.rey.material.app.Dialog;
import com.rey.material.app.SimpleDialog;
import com.rey.material.drawable.BlankDrawable;
import com.rey.material.util.ThemeUtil;
import com.rey.material.widget.CheckBox;
import com.rey.material.widget.CompoundButton;
import com.rey.material.widget.EditText;
import com.rey.material.widget.ListView;
import com.rey.material.widget.RadioButton;
import com.rey.material.widget.TextView;

import java.util.ArrayList;

import weartest.com.client.R;

/**
 * Created by alekseyg on 11/02/2016.
 */


/**
     * Created by Rey on 12/16/2014.
     */
    public class MySimpleDialog extends Dialog {

        private TextView mMessage;
   private com.rey.material.widget.EditText txt;
        private InternalScrollView mScrollView;
        private InternalListView mListView;
        private InternalAdapter mAdapter;

        private int mMessageTextAppearanceId;
        private int mMessageTextColor;

        private int mRadioButtonStyle;
        private int mCheckBoxStyle;
        private int mItemHeight;
        private int mItemTextAppearance;

        private int mMode;

        protected static final int MODE_NONE = 0;
        protected static final int MODE_MESSAGE = 1;
        protected static final int MODE_ITEMS = 2;
        protected static final int MODE_MULTI_ITEMS = 3;
        protected static final int MODE_CUSTOM = 4;

        public interface OnSelectionChangedListener{
            public void onSelectionChanged(int index, boolean selected);
        }

        private OnSelectionChangedListener mOnSelectionChangedListener;

        public MySimpleDialog(Context context) {
            super(context, R.style.Material_App_Dialog_Simple_Light);
        }

        public MySimpleDialog(Context context, int style) {
            super(context, style);
        }

        @Override
        protected void onCreate() {
        }

        @Override
        public Dialog applyStyle(int resId) {
            super.applyStyle(resId);

            if(resId == 0)
                return this;

            TypedArray a = getContext().obtainStyledAttributes(resId, R.styleable.SimpleDialog);

            messageTextAppearance(a.getResourceId(R.styleable.SimpleDialog_di_messageTextAppearance, R.style.TextAppearance_AppCompat_Body1));

            if(ThemeUtil.getType(a, R.styleable.SimpleDialog_di_messageTextColor) != TypedValue.TYPE_NULL)
                messageTextColor(a.getColor(R.styleable.SimpleDialog_di_messageTextColor, 0));

            radioButtonStyle(a.getResourceId(R.styleable.SimpleDialog_di_radioButtonStyle, 0));
            checkBoxStyle(a.getResourceId(R.styleable.SimpleDialog_di_checkBoxStyle, 0));
            itemHeight(a.getDimensionPixelSize(R.styleable.SimpleDialog_di_itemHeight, ViewGroup.LayoutParams.WRAP_CONTENT));
            itemTextAppearance(a.getResourceId(R.styleable.SimpleDialog_di_itemTextAppearance, R.style.TextAppearance_AppCompat_Body1));

            a.recycle();

            return this;
        }

        @Override
        public Dialog clearContent() {
            super.clearContent();
            mMode = MODE_NONE;
            return this;
        }

        @Override
        public Dialog title(CharSequence title){
            boolean titleVisible = !TextUtils.isEmpty(title);
            contentMargin(mContentPadding, titleVisible ? 0 : mContentPadding, mContentPadding, 0);
            return super.title(title);
        }
    LinearLayout ll;
    LinearLayout ll1;
        @Override
        public Dialog contentView(View v) {
        /*   ll = new LinearLayout(getContext());
           // ll1 = new LinearLayout(getContext());
          //  ImageView image = new ImageView(getContext());
         //   image.setImageResource(R.drawable.find);
            ll.setOrientation(LinearLayout.VERTICAL);
          //  ll1.setOrientation(LinearLayout.VERTICAL);
            txt = new com.rey.material.widget.EditText(getContext());
            textListener();
            txt.setPadding(40, 3, 3, 25);
            txt.applyStyle(R.style.Material_Widget_EditText_Light);
            txt.setText("");
          //  ll1.addView(image, 0);
          //  ll1.addView(txt,1);

            ll.addView(txt,0);


            if(mScrollView == null)
                initScrollView();

            if(mScrollView.getChildAt(0) != v && v != null) {
               *//* mScrollView.removeAllViews();
                mScrollView.addView(v);*//*
                mMode = MODE_CUSTOM;
                ll.addView(mScrollView, 1);
                super.contentView(ll);
            }

            return this;*/
            ll = new LinearLayout(getContext());
            ll1 = new LinearLayout(getContext());

            ll.setOrientation(LinearLayout.VERTICAL);
            ll1.setOrientation(LinearLayout.HORIZONTAL);
            txt = new EditText(getContext());
            textListener();
            txt.setPadding(7, 40, 40, 40);
            txt.setText("");
            ll.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            ll1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            txt.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            txt.setHint("Your Country");
            //txt.setBackgroundColor(R.color.);
            txt.setScrollBarStyle(R.style.Material_Widget_EditText);
              ImageView image = new ImageView(getContext());
               image.setImageResource(R.drawable.find);
            LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.gravity=Gravity.CENTER;
            image.setLayoutParams(layoutParams);
              ll1.addView(image, 0);
              ll1.addView(txt, 1);
           /* LinearLayout.LayoutParams params= (LinearLayout.LayoutParams) v.getLayoutParams();
            params.width= LinearLayout.LayoutParams.MATCH_PARENT;
            ll1.setLayoutParams(pa);*/
            ll.addView(ll1, 0);
         //   txt.applyStyle(R.style.Material_Widget_EditText_Light);

            if(mScrollView == null)
                initScrollView();

            if(mScrollView.getChildAt(0) != v && v != null) {
               /* mScrollView.removeAllViews();
                mScrollView.addView(v);*/
                mMode = MODE_CUSTOM;
                ll.addView(mScrollView, 1);
                super.contentView(ll);
            }

            return this;
        }

        private void initScrollView(){
            mScrollView = new InternalScrollView(getContext());
            mScrollView.setPadding(0, 0, 0, mContentPadding - mActionPadding);
            mScrollView.setClipToPadding(false);
            mScrollView.setFillViewport(true);
            mScrollView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        }

        private void initMessageView(){
            mMessage = new TextView(getContext());
            mMessage.setTextAppearance(getContext(), mMessageTextAppearanceId);
            mMessage.setTextColor(mMessageTextColor);
            mMessage.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
        }

        public MySimpleDialog message(CharSequence message){
            if(mScrollView == null)
                initScrollView();

            if(mMessage == null)
                initMessageView();

            if(mScrollView.getChildAt(0) != mMessage) {
                mScrollView.removeAllViews();
                mScrollView.addView(mMessage);
            }

            mMessage.setText(message);
            if(!TextUtils.isEmpty(message)) {
                mMode = MODE_MESSAGE;
                ll.addView(mScrollView);
                super.contentView(ll);
            }
            return this;
        }

        public MySimpleDialog message(int id){
            return message(id == 0 ? null : getContext().getResources().getString(id));
        }

        public MySimpleDialog messageTextAppearance(int resId){
            if(mMessageTextAppearanceId != resId){
                mMessageTextAppearanceId = resId;
                if(mMessage != null)
                    mMessage.setTextAppearance(getContext(), mMessageTextAppearanceId);
            }
            return this;
        }

        public MySimpleDialog messageTextColor(int color){
            if(mMessageTextColor != color){
                mMessageTextColor = color;
                if(mMessage != null)
                    mMessage.setTextColor(color);
            }
            return this;
        }

        public MySimpleDialog radioButtonStyle(int resId){
            if(mRadioButtonStyle != resId){
                mRadioButtonStyle = resId;
                if(mAdapter != null && mMode == MODE_ITEMS)
                    mAdapter.notifyDataSetChanged();
            }
            return this;
        }

        public MySimpleDialog checkBoxStyle(int resId){
            if(mCheckBoxStyle != resId){
                mCheckBoxStyle = resId;
                if(mAdapter != null && mMode == MODE_MULTI_ITEMS)
                    mAdapter.notifyDataSetChanged();
            }
            return this;
        }

        public MySimpleDialog itemHeight(int height){
            if(mItemHeight != height){
                mItemHeight = height;
                if(mAdapter != null)
                    mAdapter.notifyDataSetChanged();
            }
            return this;
        }

        public MySimpleDialog itemTextAppearance(int resId){
            if(mItemTextAppearance != resId){
                mItemTextAppearance = resId;
                if(mAdapter != null)
                    mAdapter.notifyDataSetChanged();
            }
            return this;
        }

        private void initListView(){


            mListView = new InternalListView(getContext());
            mListView.setDividerHeight(0);
            mListView.setCacheColorHint(0x00000000);
            mListView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
            mListView.setClipToPadding(false);
            mListView.setSelector(BlankDrawable.getInstance());
            mListView.setPadding(0, 0, 0, mContentPadding - mActionPadding);
            mListView.setVerticalFadingEdgeEnabled(false);
            mListView.setOverScrollMode(ListView.OVER_SCROLL_NEVER);

            mAdapter = new InternalAdapter();
            mListView.setAdapter(mAdapter);
        }

        public MySimpleDialog items(CharSequence[] items, int selectedIndex){
            if(mListView == null)
                initListView();

            mMode = MODE_ITEMS;
            mAdapter.setItems(items, selectedIndex);
            ll.addView(mListView);
            super.contentView(ll);
            return this;
        }

        public MySimpleDialog multiChoiceItems(CharSequence[] items, int... selectedIndexes){
            if(mListView == null)
                initListView();

            mMode = MODE_MULTI_ITEMS;
            mAdapter.setItems(items, selectedIndexes);
            ll.addView(mListView);
            super.contentView(ll);
            return this;
        }

        public MySimpleDialog onSelectionChangedListener(OnSelectionChangedListener listener){
            mOnSelectionChangedListener = listener;
            return this;
        }

        public int[] getSelectedIndexes(){
            return mAdapter == null ? null : mAdapter.getSelectedIndexes();
        }

        public CharSequence[] getSelectedValues(){
            return mAdapter.getSelectedValues();
        }

        public int getSelectedIndex(){
            return mAdapter == null ? -1 : mAdapter.getLastSelectedIndex();
        }

        public CharSequence getSelectedValue(){
            return mAdapter.getLastSelectedValue();
        }

        private class InternalScrollView extends ScrollView {

            public InternalScrollView(Context context) {
                super(context);
            }

            @Override
            protected void onLayout(boolean changed, int l, int t, int r, int b) {
                super.onLayout(changed, l, t, r, b);

                View child = getChildAt(0);
                showDivider(child != null && child.getMeasuredHeight() > getMeasuredHeight() - getPaddingTop() - getPaddingBottom());
            }
        }

        private class InternalListView extends ListView{

            public InternalListView(Context context) {
                super(context);
            }

            @Override
            protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
                int heightMode = MeasureSpec.getMode(heightMeasureSpec);
                if(heightMode == MeasureSpec.UNSPECIFIED){
                    if(mItemHeight != ViewGroup.LayoutParams.WRAP_CONTENT)
                        heightMeasureSpec = MeasureSpec.makeMeasureSpec(mItemHeight * getAdapter().getCount() + getPaddingTop() + getPaddingBottom(), MeasureSpec.EXACTLY);
                }

                super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            }



            @Override
            protected void onLayout(boolean changed, int l, int t, int r, int b) {
                super.onLayout(changed, l, t, r, b);

                int totalHeight = 0;
                int childCount = getChildCount();

                for(int i = 0; i < childCount; i++)
                    totalHeight += getChildAt(i).getMeasuredHeight();

                showDivider(totalHeight > getMeasuredHeight() || (totalHeight == getMeasuredHeight() && getAdapter().getCount() > childCount));
            }

        }

        private class InternalAdapter extends BaseAdapter implements CompoundButton.OnCheckedChangeListener{

            private CharSequence[] mItems;
            private boolean[] mSelected;
            private int mLastSelectedIndex;

            public void setItems(CharSequence[] items, int... selectedIndexes){
                mItems = items;

                if(mSelected == null ||  mSelected.length != items.length)
                    mSelected = new boolean[items.length];

                for(int i = 0; i < mSelected.length; i++)
                    mSelected[i] = false;

                if(selectedIndexes != null)
                    for(int index : selectedIndexes)
                        if(index >= 0 && index < mSelected.length) {
                            mSelected[index] = true;
                            mLastSelectedIndex = index;
                        }

                notifyDataSetChanged();
            }

            public int getLastSelectedIndex(){
                return mLastSelectedIndex;
            }

            public CharSequence getLastSelectedValue(){
                return mItems[mLastSelectedIndex];
            }

            public int[] getSelectedIndexes(){
                int count = 0;
                for(int i = 0; i < mSelected.length; i++)
                    if(mSelected[i])
                        count++;

                if(count == 0)
                    return null;

                int[] result = new int[count];
                count = 0;
                for(int i = 0; i < mSelected.length; i++)
                    if(mSelected[i]){
                        result[count] = i;
                        count++;
                    }

                return result;
            }

            public CharSequence[] getSelectedValues(){
                int count = 0;
                for(int i = 0; i < mSelected.length; i++)
                    if(mSelected[i])
                        count++;

                if(count == 0)
                    return null;

                CharSequence[] result = new CharSequence[count];
                count = 0;
                for(int i = 0; i < mSelected.length; i++)
                    if(mSelected[i]){
                        result[count] = mItems[i];
                        count++;
                    }

                return result;
            }

            @Override
            public int getCount() {
                return mItems == null ? 0 : mItems.length;
            }

            @Override
            public Object getItem(int position) {
                return mItems == null ? 0 : mItems[position];
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                CompoundButton v = (CompoundButton)convertView;
                if(v == null) {
                    v = (mMode == MODE_MULTI_ITEMS) ? new CheckBox(parent.getContext(), null, 0, mCheckBoxStyle) : new RadioButton(parent.getContext(), null, 0, mRadioButtonStyle);
                    if(mItemHeight != ViewGroup.LayoutParams.WRAP_CONTENT)
                        v.setMinHeight(mItemHeight);
                    v.setGravity(Gravity.START|Gravity.CENTER_VERTICAL);
                    v.setTextAppearance(v.getContext(), mItemTextAppearance);
                    v.setPadding(mContentPadding, 0, 0, 0);
                }

                v.setTag(position);
                v.setText(mItems[position]);
                if(v instanceof CheckBox)
                    ((CheckBox) v).setCheckedImmediately(mSelected[position]);
                else
                    ((RadioButton) v).setCheckedImmediately(mSelected[position]);

                v.setOnCheckedChangeListener(this);

                return v;
            }

            @Override
            public void onCheckedChanged(android.widget.CompoundButton v, boolean isChecked) {
                int position = (Integer)v.getTag();
                if(mSelected[position] != isChecked) {
                    mSelected[position] = isChecked;

                    if(mOnSelectionChangedListener != null)
                        mOnSelectionChangedListener.onSelectionChanged(position, mSelected[position]);
                }

                if(mMode == MODE_ITEMS && isChecked && mLastSelectedIndex != position){
                    mSelected[mLastSelectedIndex] = false;

                    if(mOnSelectionChangedListener != null)
                        mOnSelectionChangedListener.onSelectionChanged(mLastSelectedIndex, false);

                    CompoundButton child = (CompoundButton) mListView.getChildAt(mLastSelectedIndex - mListView.getFirstVisiblePosition());
                    if(child != null)
                        child.setChecked(false);

                    mLastSelectedIndex = position;
                }
            }
        }
    static CharSequence[] list;
    protected static CharSequence[] mItems;
        public static class Builder extends Dialog.Builder implements OnSelectionChangedListener {

            protected int mMode;
            protected CharSequence mMessage;

            protected int[] mSelectedIndexes;

            public Builder(){
                super(R.style.Material_App_Dialog_Simple_Light);
            }

            public Builder(int styleId){
                super(styleId);
            }

            public Builder message(CharSequence message){
                mMode = MODE_MESSAGE;
                mMessage = message;
                return this;
            }

            public Builder items(CharSequence[] items, int selectedIndex){
                mMode = MODE_ITEMS;
                mItems = items;
                list = items;
                mSelectedIndexes = new int[]{selectedIndex};
                return this;
            }

            public Builder multiChoiceItems(CharSequence[] items, int... selectedIndexes){
                mMode = MODE_MULTI_ITEMS;
                mItems = items;
                mSelectedIndexes = selectedIndexes;
                return this;
            }

            public int getSelectedIndex(){
                if(mMode == MODE_ITEMS || mMode == MODE_MULTI_ITEMS)
                    return mSelectedIndexes[0];

                return -1;
            }
//start change
            public CharSequence getSelectedValue(){
                int index = getSelectedIndex();
                if(!newList.isEmpty())
                return index >= 0 ? newList.get(index) :"-1";
                return "-1";
            }

            public int[] getSelectedIndexes(){
                if(mMode == MODE_ITEMS || mMode == MODE_MULTI_ITEMS)
                    return mSelectedIndexes;

                return null;
            }

            public CharSequence[] getSelectedValues(){
                int[] indexes = getSelectedIndexes();
                if(indexes == null || indexes.length == 0)
                    return null;

                CharSequence[] result = new CharSequence[indexes.length];
                for(int i = 0; i < indexes.length; i++)
                    result[i] = mItems[indexes[i]];

                return result;
            }

            @Override
            protected Dialog onBuild(Context context, int styleId) {
                MySimpleDialog dialog = new MySimpleDialog(context, styleId);

                switch (mMode){
                    case MODE_MESSAGE:
                        dialog.message(mMessage);
                        break;
                    case MODE_ITEMS:
                        dialog.items(mItems, mSelectedIndexes == null ? 0 : mSelectedIndexes[0]);
                        dialog.onSelectionChangedListener(this);
                        break;
                    case MODE_MULTI_ITEMS:
                        dialog.multiChoiceItems(mItems, mSelectedIndexes);
                        dialog.onSelectionChangedListener(this);
                        break;
                }

                return dialog;
            }

            @Override
            public void onSelectionChanged(int index, boolean selected) {
                switch (mMode){
                    case MODE_ITEMS:
                        if(selected) {
                            if (mSelectedIndexes == null)
                                mSelectedIndexes = new int[]{index};
                            else
                                mSelectedIndexes[0] = index;
                        }
                        break;
                    case MODE_MULTI_ITEMS:
                        mSelectedIndexes = ((SimpleDialog)mDialog).getSelectedIndexes();
                        break;
                }
            }

            protected Builder(Parcel in) {
                super(in);
            }

            @Override
            protected void onReadFromParcel(Parcel in) {
                mMode = in.readInt();
                switch (mMode){
                    case MODE_MESSAGE:
                        mMessage = (CharSequence)in.readParcelable(null);
                        break;
                    case MODE_ITEMS: {
                        Parcelable[] values = in.readParcelableArray(null);
                        if (values != null && values.length > 0) {
                            mItems = new CharSequence[values.length];
                            for (int i = 0; i < mItems.length; i++)
                                mItems[i] = (CharSequence) values[i];
                        } else
                            mItems = null;
                        mSelectedIndexes = new int[]{in.readInt()};
                        break;
                    }
                    case MODE_MULTI_ITEMS: {
                        Parcelable[] values = in.readParcelableArray(null);
                        if (values != null && values.length > 0) {
                            mItems = new CharSequence[values.length];
                            for (int i = 0; i < mItems.length; i++)
                                mItems[i] = (CharSequence) values[i];
                        } else
                            mItems = null;
                        int length = in.readInt();
                        if(length > 0) {
                            mSelectedIndexes = new int[length];
                            in.readIntArray(mSelectedIndexes);
                        }
                        break;
                    }
                }
            }

            @Override
            protected void onWriteToParcel(Parcel dest, int flags) {
                dest.writeInt(mMode);
                switch (mMode){
                    case MODE_MESSAGE:
                        dest.writeValue(mMessage);
                        break;
                    case MODE_ITEMS:
                        dest.writeArray(mItems);
                        dest.writeInt(mSelectedIndexes == null ? 0 : mSelectedIndexes[0]);
                        break;
                    case MODE_MULTI_ITEMS:
                        dest.writeArray(mItems);
                        int length = mSelectedIndexes == null ? 0 : mSelectedIndexes.length;
                        dest.writeInt(length);
                        if(length > 0)
                            dest.writeIntArray(mSelectedIndexes);
                        break;
                }
            }

            public static final Parcelable.Creator<Builder> CREATOR = new Parcelable.Creator<Builder>() {
                public Builder createFromParcel(Parcel in) {
                    return new Builder(in);
                }

                public Builder[] newArray(int size) {
                    return new Builder[size];
                }
            };

        }
  static  ArrayList<String> newList = new ArrayList<String>();
void addToNewList(int i)
{
    newList.add((String) list[i]);
}
    void changeListView()
    {
        if(mAdapter!=null&&mListView!=null){
            String [] ss = new String[newList.size()];
            for(int i=0;i<newList.size();i++)
            {
                ss[i]=newList.get(i);
            }


            initListView();

            mMode = MODE_ITEMS;
            mAdapter.setItems(ss, 0);

            ll.removeViewAt(1);
            ll.addView(mListView);

        }
    }

    void  textListener()
    {
        if(newList!=null)
        newList.clear();
         txt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            public void afterTextChanged(Editable s) {


                newList.clear();
                for (int i = 0; i < list.length; i++) {
                    if(list[i].length()>s.length()) {
                        String s1= ((String) list[i].subSequence(0, s.length())).toLowerCase();
                        String s2 = s.toString().toLowerCase();

                        if (s1.equals(s2)) {
                            addToNewList(i);
                        }
                    }
                }
                        changeListView();


            }
        });
    }
    }

