package com.example.arsalan.mygym.fragments;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.example.arsalan.mygym.Objects.CityNState;
import com.example.arsalan.mygym.Objects.News;
import com.example.arsalan.mygym.Objects.Province;
import com.example.arsalan.mygym.R;
import com.example.arsalan.mygym.adapters.AdapterNews;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MyGymFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MyGymFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyGymFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public MyGymFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GymListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyGymFragment newInstance(String param1, String param2) {
        MyGymFragment fragment = new MyGymFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_my_gym, container, false);
        RecyclerView newsRV = v.findViewById(R.id.rvNews);
        //لیست جدیدترین خبر ها
        List<News> newsList = new ArrayList<>();
        for (int i=0;i<20;i++)
            newsList.add(new News());

        AdapterNews adapter = new AdapterNews(getActivity(), newsList);
        newsRV.setAdapter(adapter);
        newsRV.setLayoutManager(new LinearLayoutManager(getActivity()));


        v.setRotation(180);
        return v;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } /*else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    class ProvinceAdapter implements SpinnerAdapter {

        List<Province> provinceList;

        public ProvinceAdapter() {
            provinceList = new ArrayList<>();
            this.provinceList.addAll(CityNState.getProvinceList());
        }

        @Override
        public View getDropDownView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = new TextView(getContext());
                Typeface typeface = ResourcesCompat.getFont(getContext(), R.font.iran_sans_mobile);

                ((TextView) view).setTypeface(typeface);
                ((TextView) view).setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
                ((TextView) view).setTextColor(Color.BLACK);
            }
            ((TextView) view).setText(getItem(i).getName());
            return view;
        }

        @Override
        public void registerDataSetObserver(DataSetObserver dataSetObserver) {

        }

        @Override
        public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {

        }

        @Override
        public int getCount() {
            return provinceList.size();
        }

        @Override
        public Province getItem(int i) {
            return provinceList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return provinceList.get(i).getId();
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = new TextView(getContext());
                Typeface typeface = ResourcesCompat.getFont(getContext(), R.font.iran_sans_mobile);

                ((TextView) view).setTypeface(typeface);
                ((TextView) view).setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
                ((TextView) view).setTextColor(Color.BLACK);
            }
            if (i == 0) {
                ((TextView) view).setText("استان خود را انتخاب نمایید");
                return view;
            }
            ((TextView) view).setText(getItem(i-1).getName());
            return view;
        }

        @Override
        public int getItemViewType(int i) {
            return 0;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }
    }
}
