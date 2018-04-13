package com.example.arsalan.mygym.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.ToggleButton;

import com.example.arsalan.mygym.Objects.Gym;
import com.example.arsalan.mygym.Objects.RetGymList;
import com.example.arsalan.mygym.R;
import com.example.arsalan.mygym.adapters.AdapterGymList;
import com.example.arsalan.mygym.adapters.AdapterProvinceSp;
import com.example.arsalan.mygym.retrofit.ApiClient;
import com.example.arsalan.mygym.retrofit.ApiInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GymListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link GymListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GymListFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private ArrayList<Gym> gyms;
    private AdapterGymList adapter;

    private ToggleButton sortByCityBtn;
    private ToggleButton sortByPointsBtn;

    public GymListFragment() {
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
    public static GymListFragment newInstance(String param1, String param2) {
        GymListFragment fragment = new GymListFragment();
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
        View v = inflater.inflate(R.layout.fragment_gym_list, container, false);
        RecyclerView rv = v.findViewById(R.id.rvTrainers);
        gyms = new ArrayList<>();


        adapter = new AdapterGymList(getActivity(), gyms);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        Spinner provinceSpn = v.findViewById(R.id.spnProvince);
        provinceSpn.setPrompt("استان خود را انتخاب نمایید");

        provinceSpn.setAdapter(new AdapterProvinceSp());
        getGymWeb(0, 1);

        sortByCityBtn = v.findViewById(R.id.btnSortByPoints);
        sortByPointsBtn = v.findViewById(R.id.btnSortByCity);
        sortByCityBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                compoundButton.setEnabled(!b);
                sortByPointsBtn.setChecked(!b);
                getGymWeb(0, b ? 1 : 2);

            }
        });
        sortByPointsBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                sortByCityBtn.setChecked(!b);
                compoundButton.setEnabled(!b);

            }
        });

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

    private void getGymWeb(int cityId, int sortType) {
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        final ProgressDialog waitingDialog = new ProgressDialog(getContext());
        waitingDialog.setMessage("لظفا چند لحظه منتظر بمانبد...");
        waitingDialog.show();
        Call<RetGymList> call = apiService.getGymList(0, 10, cityId, sortType);
        call.enqueue(new Callback<RetGymList>() {
            @Override
            public void onResponse(Call<RetGymList> call, Response<RetGymList> response) {
                waitingDialog.dismiss();
                if (response.isSuccessful())
                    Log.d("getNewsWeb", "onResponse: records:" + response.body().getRecordsCount());
                gyms.removeAll(gyms);
                gyms.addAll(response.body().getRecords());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<RetGymList> call, Throwable t) {
                waitingDialog.dismiss();

            }
        });

    }


}
