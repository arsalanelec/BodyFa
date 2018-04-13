package com.example.arsalan.mygym.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.arsalan.mygym.Objects.CityNState;
import com.example.arsalan.mygym.Objects.Province;
import com.example.arsalan.mygym.Objects.RetTrainerList;
import com.example.arsalan.mygym.Objects.Trainer;
import com.example.arsalan.mygym.R;
import com.example.arsalan.mygym.adapters.AdapterTrainers;
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
 * {@link TrainerListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TrainerListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TrainerListFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private List<Trainer> trainerList;
    private OnFragmentInteractionListener mListener;
    private AdapterTrainers adapter;

    private ToggleButton byMedalBtn;
    private ToggleButton byRankBtn;

    public TrainerListFragment() {
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
    public static TrainerListFragment newInstance(String param1, String param2) {
        TrainerListFragment fragment = new TrainerListFragment();
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
        View v = inflater.inflate(R.layout.fragment_trainer_list, container, false);
        RecyclerView rv = v.findViewById(R.id.rvTrainers);
        trainerList = new ArrayList<>();
        adapter = new AdapterTrainers(getActivity(), trainerList);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new GridLayoutManager(getActivity(), 3));

        Spinner provinceSpn = v.findViewById(R.id.spnProvince);
        provinceSpn.setPrompt("استان خود را انتخاب نمایید");

        provinceSpn.setAdapter(new ProvinceAdapter());

        byMedalBtn = v.findViewById(R.id.btnByMedals);
        byRankBtn = v.findViewById(R.id.btnByRank);
        byMedalBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                byRankBtn.setChecked(!b);
                getTrainerWeb(0, 0, b ? 1 : 2);
                compoundButton.setEnabled(!b);
            }
        });
        byRankBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                byMedalBtn.setChecked(!b);
                compoundButton.setEnabled(!b);
            }
        });

        getTrainerWeb(0, 0, 1);
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

    private void getTrainerWeb(int cityId, int gymId, int sortType) {
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        final ProgressDialog waitingDialog = new ProgressDialog(getContext());
        waitingDialog.setMessage("لظفا چند لحظه منتظر بمانبد...");
        waitingDialog.show();
        Call<RetTrainerList> call = apiService.getTrainerList(0, 10, gymId, cityId, sortType);
        call.enqueue(new Callback<RetTrainerList>() {
            @Override
            public void onResponse(Call<RetTrainerList> call, Response<RetTrainerList> response) {
                waitingDialog.dismiss();
                if (response.isSuccessful())
                    Log.d("getNewsWeb", "onResponse: records:" + response.body().getRecordsCount());
                trainerList.removeAll(trainerList);
                trainerList.addAll(response.body().getRecords());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<RetTrainerList> call, Throwable t) {
                waitingDialog.dismiss();

            }
        });

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
            ((TextView) view).setText(getItem(i - 1).getName());
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
