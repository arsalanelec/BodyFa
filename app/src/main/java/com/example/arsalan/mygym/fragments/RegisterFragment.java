package com.example.arsalan.mygym.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.example.arsalan.mygym.MyApplication;
import com.example.arsalan.mygym.Objects.RetroResult;
import com.example.arsalan.mygym.R;
import com.example.arsalan.mygym.adapters.AdapterCitySp;
import com.example.arsalan.mygym.adapters.AdapterProvinceSp;
import com.example.arsalan.mygym.retrofit.ApiClient;
import com.example.arsalan.mygym.retrofit.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RegisterFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    public static final int KEY_VARZESHKAR = 0;
    public static final int KEY_MORABI = 1;
    public static final int KEY_BASHGAH = 2;

    private int mChoice;
    private String role;
    private OnFragmentInteractionListener mListener;

    public RegisterFragment() {
        // Required empty public constructor
    }

    public static RegisterFragment newInstance(int choice) {
        RegisterFragment fragment = new RegisterFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, choice);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mChoice = getArguments().getInt(ARG_PARAM1);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_register, container, false);
        TextView loginTV = v.findViewById(R.id.txtLogin);
        TextView titleTv = v.findViewById(R.id.txtTitle);


        final Spinner daySp = v.findViewById(R.id.spDateDay);
        List<Integer> days = new ArrayList<>();
        for (int i = 1; i < 32; i++) days.add(i);
        daySp.setAdapter(new MySpinnerAdapter(days));

        List<Integer> months = new ArrayList<>();
        for (int i = 1; i <= 12; i++) months.add(i);
        final Spinner monthSp = v.findViewById(R.id.spDateMont);
        monthSp.setAdapter(new MySpinnerAdapter(months));

        final Spinner yearSp = v.findViewById(R.id.spDateYear);
        List<Integer> years = new ArrayList<>();
        for (int i = 1397; i > 1307; i--) years.add(i);
        yearSp.setAdapter(new MySpinnerAdapter(years));

        final Spinner citySp = v.findViewById(R.id.spCity);

        //استانها
        Spinner provinceSp = v.findViewById(R.id.spProvince);
        provinceSp.setAdapter(new AdapterProvinceSp());
        provinceSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                citySp.setAdapter(new AdapterCitySp(l));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        // بازگشت به ورود
        loginTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.gotoLoginPage();
            }
        });

        //موبایل یا ایمیل
        RadioGroup radioGroup = v.findViewById(R.id.rgEmailMobile);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (((RadioButton) radioGroup.findViewById(R.id.rbEmail)).isChecked()) {
                    ((EditText) v.findViewById(R.id.etEmail)).setEnabled(true);
                    ((EditText) v.findViewById(R.id.etMobile)).setEnabled(false);
                } else if (((RadioButton) radioGroup.findViewById(R.id.rbMobile)).isChecked()) {
                    ((EditText) v.findViewById(R.id.etEmail)).setEnabled(false);
                    ((EditText) v.findViewById(R.id.etMobile)).setEnabled(true);
                }
            }
        });
        final EditText phoneET = v.findViewById(R.id.etPhone);
        final EditText addressET = v.findViewById(R.id.etAddress);
        switch (mChoice) {
            case 0:
                titleTv.setText("(ورزشکار)");
                phoneET.setVisibility(View.GONE);
                addressET.setVisibility(View.GONE);
                role = "athlete";
                break;
            case 1:
                titleTv.setText("(مربی)");
                phoneET.setVisibility(View.GONE);
                addressET.setVisibility(View.GONE);
                role = "trainer";
                break;
            case 2:
                titleTv.setText("(مدیر باشگاه)");
                phoneET.setVisibility(View.VISIBLE);
                addressET.setVisibility(View.VISIBLE);
                role = "gym";
                break;
        }
        final EditText usernameET = v.findViewById(R.id.etUsername);
        final EditText passwordET = v.findViewById(R.id.etPassword);
        final EditText passwordConfirmET = v.findViewById(R.id.etConfirmPass);
        final EditText nameET = v.findViewById(R.id.etName);
        final EditText emailET = v.findViewById(R.id.etEmail);
        final EditText mobileET = v.findViewById(R.id.etMobile);
        final EditText weightET = v.findViewById(R.id.etWeight);

        final RadioButton maleRB = v.findViewById(R.id.rbMale);

        Button registerBtn = v.findViewById(R.id.btnRegister);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean hasError = false;
                if (usernameET.length() < 6) {
                    usernameET.setError("نام کاربری باید حداقل 6 حرف باشد");
                    hasError = true;
                }
                if (nameET.length() < 6) {
                    nameET.setError("نام و نام خانوادگی خود را وارد نمایید");
                    hasError = true;
                }
                if (passwordET.length() < 6) {
                    passwordET.setError("رمز ورود باید حداقل 6 حرف باشد");
                    hasError = true;
                } else if (!passwordConfirmET.getText().toString().equals(passwordET.getText().toString())) {
                    passwordConfirmET.setError("رمز ورود با تکرار آن یکسان نیست");
                    hasError = true;
                }
                if (((RadioButton) v.findViewById(R.id.rbEmail)).isChecked() && !android.util.Patterns.EMAIL_ADDRESS.matcher(emailET.getText()).matches()) {
                    hasError = true;
                    emailET.setError("ایمیل وارد شده صحیح نیست");
                }
                if (((RadioButton) v.findViewById(R.id.rbMobile)).isChecked() && mobileET.length() < 10) {
                    mobileET.setError("شماره همراه را بصورت 09xxxxxxxxx وارد نمایید");
                    hasError = true;
                }
                if (weightET.getText().toString().isEmpty()) {
                    weightET.setText("0");
                }
                if (hasError) return;

                ApiInterface apiService =
                        ApiClient.getClient().create(ApiInterface.class);


                final ProgressDialog waitingDialog = new ProgressDialog(getContext());
                waitingDialog.setMessage("لظفا چند لحظه منتظر بمانبد...");
                waitingDialog.show();
                Log.d("Activation", "user id:\""+((MyApplication) getActivity().getApplication()).getCurrentUser().getId()
                +"\" mobile:"+mobileET.getText().toString());

                Call<RetroResult> call = apiService.getActivationCode("Bearer " + ((MyApplication) getActivity().getApplication()).getCurrentToken().getToken(), 1, Long.parseLong(mobileET.getText().toString()));

                call.enqueue(new Callback<RetroResult>() {
                    @Override
                    public void onResponse(Call<RetroResult> call, Response<RetroResult> response) {
                        waitingDialog.dismiss();
                        if(response.isSuccessful()
                                && response.body().getResult()!=null
                                && response.body().getResult().contains("OK"))
                            Log.d("Activation", "onResponse: Code sent!");
                        else
                            Log.d("Activation", "onResponse: error:"+response.body().getResult());

                    }

                    @Override
                    public void onFailure(Call<RetroResult> call, Throwable t) {
                        waitingDialog.dismiss();
                        Log.d("Activation", "onFailure: "+t.getMessage());

                    }
                });




/*                RequestBody userNameReq = RequestBody.create(MediaType.parse("text/plain"), usernameET.getText().toString());
                RequestBody bodyPassword = RequestBody.create(MediaType.parse("text/plain"), passwordET.getText().toString());
                RequestBody nameReq = RequestBody.create(MediaType.parse("text/plain"), nameET.getText().toString());
                RequestBody genderReq = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(maleRB.isChecked()));
                RequestBody roleReq = RequestBody.create(MediaType.parse("text/plain"), role);
                RequestBody weightReq = RequestBody.create(MediaType.parse("text/plain"), weightET.getText().toString());
                RequestBody cityReq = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(citySp.getSelectedItemId()));
                RequestBody mobileReq = RequestBody.create(MediaType.parse("text/plain"), mobileET.getText().toString());
                RequestBody emailReq = RequestBody.create(MediaType.parse("text/plain"), emailET.getText().toString());
                RequestBody birthDayReq = RequestBody.create(MediaType.parse("text/plain"),yearSp.getSelectedItem()+"/"+monthSp.getSelectedItem()+"/"+daySp.getSelectedItem());

                Map<String, RequestBody> requestBodyMap = new HashMap<>();
                requestBodyMap.put("username", userNameReq);
                requestBodyMap.put("password", bodyPassword);
                requestBodyMap.put("name", nameReq);
                requestBodyMap.put("gender", genderReq);
                requestBodyMap.put("roleName", roleReq);
                requestBodyMap.put("weight", weightReq);
                requestBodyMap.put("BirthDateFa", birthDayReq);

                requestBodyMap.put("cityId", cityReq);
                if (role.equals("gym")) {
                    RequestBody addressReq = RequestBody.create(MediaType.parse("text/plain"), addressET.getText().toString());
                    requestBodyMap.put("address",addressReq );

                    RequestBody phoneReq = RequestBody.create(MediaType.parse("text/plain"), phoneET.getText().toString());
                    requestBodyMap.put("phone",phoneReq );

                }
                if (((RadioButton) v.findViewById(R.id.rbMobile)).isChecked()) {

                    requestBodyMap.put("mobile",mobileReq );

                }
                if (((RadioButton) v.findViewById(R.id.rbEmail)).isChecked()){
                    requestBodyMap.put("email",emailReq );

                }

                final ProgressDialog waitingDialog = new ProgressDialog(getContext());
                waitingDialog.setMessage("لظفا چند لحظه منتظر بمانبد...");
                waitingDialog.show();
                Call<RetroResult> call = apiService.registerOnServer(requestBodyMap);
                call.enqueue(new Callback<RetroResult>() {
                    @Override
                    public void onResponse(Call<RetroResult> call, Response<RetroResult> response) {
                        waitingDialog.dismiss();
                        if (response.isSuccessful()) {
                            switch (response.body().getResult()) {
                                case "OK":
                                    Toast.makeText(getContext(), "ثبت نام با موفقیت انجام شد.\nدرحال ورود...", Toast.LENGTH_LONG).show();
                                    mListener.login(usernameET.getText().toString(),passwordET.getText().toString());
                                    break;
                                case "ERROR":
                                    Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();

                                    break;
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<RetroResult> call, Throwable t) {
                        waitingDialog.dismiss();
                    }
                });*/

            }
        });
        return v;
    }

    public interface OnFragmentInteractionListener {
        void register();

        void login(String username, String password);

        void gotoLoginPage();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private class MySpinnerAdapter implements SpinnerAdapter {
        List<Integer> nums;

        public MySpinnerAdapter(List<Integer> nums) {
            this.nums = nums;
        }

        @Override
        public View getDropDownView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = LayoutInflater.from(getContext()).inflate(R.layout.item_spinner_date_drop, viewGroup, false);
            }
            TextView textView = view.findViewById(R.id.textView);
            textView.setText(String.valueOf(nums.get(i)));
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
            return nums.size();
        }

        @Override
        public Integer getItem(int i) {
            return nums.get(i);
        }

        @Override
        public long getItemId(int i) {
            return nums.get(i);
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = LayoutInflater.from(getContext()).inflate(R.layout.item_spinner_date, viewGroup, false);
            }
            TextView textView = view.findViewById(R.id.textView);
            textView.setText(String.valueOf(nums.get(i)));
            return view;
        }

        @Override
        public int getItemViewType(int i) {
            return 1;
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
