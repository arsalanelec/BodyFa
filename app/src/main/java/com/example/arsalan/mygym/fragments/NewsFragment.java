package com.example.arsalan.mygym.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.example.arsalan.mygym.Objects.News;
import com.example.arsalan.mygym.Objects.RetNewsList;
import com.example.arsalan.mygym.R;
import com.example.arsalan.mygym.adapters.AdapterNews;
import com.example.arsalan.mygym.retrofit.ApiClient;
import com.example.arsalan.mygym.retrofit.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NewsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NewsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private List<News> newsList;
    private AdapterNews adapter;

    public NewsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewsFragment newInstance(String param1, String param2) {
        NewsFragment fragment = new NewsFragment();
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
        View v = inflater.inflate(R.layout.fragment_news, container, false);
        RecyclerView newsRV = v.findViewById(R.id.rvNews);
        newsList = new ArrayList<>();
        /*for (int i = 0; i < 20; i++)
            newsList.add(new News());*/

         adapter = new AdapterNews(getActivity(), newsList);
        newsRV.setAdapter(adapter);
        newsRV.setLayoutManager(new LinearLayoutManager(getActivity()));
        v.setRotation(180);

        final ToggleButton foodNewsTgl = v.findViewById(R.id.btnFoodNews);

        final ToggleButton fitnessNewsTgl = v.findViewById(R.id.btnFitnessNews);
        fitnessNewsTgl.setChecked(true);
        getNewsWeb(1);
        foodNewsTgl.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    fitnessNewsTgl.setChecked(false);
                    getNewsWeb(1);
                }
            }
        });
        fitnessNewsTgl.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                foodNewsTgl.setChecked(false);
                getNewsWeb(2);
            }
        });

        return v;
    }

private void getNewsWeb(int typeId){
    ApiInterface apiService =
            ApiClient.getClient().create(ApiInterface.class);
    final ProgressDialog waitingDialog = new ProgressDialog(getContext());
    waitingDialog.setMessage("لظفا چند لحظه منتظر بمانبد...");
    waitingDialog.show();
    Call<RetNewsList> call = apiService.getNewsList(0,10,typeId);
    call.enqueue(new Callback<RetNewsList>() {
        @Override
        public void onResponse(Call<RetNewsList> call, Response<RetNewsList> response) {
            waitingDialog.dismiss();
            if(response.isSuccessful())
            Log.d("getNewsWeb", "onResponse: records:"+response.body().getRecordsCount());
            newsList.removeAll(newsList);
            newsList.addAll(response.body().getRecords());
            adapter.notifyDataSetChanged();
        }

        @Override
        public void onFailure(Call<RetNewsList> call, Throwable t) {
            waitingDialog.dismiss();

        }
    });

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
}
