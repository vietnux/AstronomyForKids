    package com.example.astronomyforkids.adapters;

    import android.annotation.SuppressLint;
    import android.content.Context;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.BaseAdapter;
    import android.widget.TextView;

    import com.example.astronomyforkids.R;

    import java.util.ArrayList;


    public class ResultsAdapter extends BaseAdapter {
        private LayoutInflater layoutInflater;
        private ArrayList<String> resultList;

        /**
         * Конструктор адапатера результатів ініціалізує зміні
         * @param context
         * @param resultList
         */
        public ResultsAdapter(Context context, ArrayList<String> resultList) {
            this.resultList = resultList;
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return resultList.size();
        }

        @Override
        public Object getItem(int i) {
            return resultList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        /**
         * Задає значеня в список результатів
         * @param i
         * @param view
         * @param viewGroup
         * @return view
         */
        @SuppressLint("SetTextI18n")
        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if(view == null)
            {
                view = layoutInflater.inflate(R.layout.item_list_view_results, viewGroup, false);
            }
            String[] results = resultList.get(i).split("/");
            System.out.println("res[0]: " + results[0]);
            System.out.println("res[1]: " + results[1]);
            ((TextView) view.findViewById(R.id.textViewItemResult)).setText(results[0]);
            ((TextView) view.findViewById(R.id.textViewItemCount)).setText(results[1] + " бали");
            return view;
        }
    }
