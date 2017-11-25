package com.capri4physio.addbranch;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.capri4physio.R;
import com.capri4physio.Services.GetWebServicesFragment;
import com.capri4physio.Services.WebServiceBaseFragment;
import com.capri4physio.Services.WebServicesCallBack;
import com.capri4physio.adapter.BranchAdapte;
import com.capri4physio.model.branch.BranchPOJO;
import com.capri4physio.net.ApiConfig;
import com.capri4physio.util.TagUtils;
import com.capri4physio.util.ToastClass;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sunil on 25-09-2017.
 */

public class BranchListFragment extends Fragment implements WebServicesCallBack {
    private static final String CALL_GET_BRANCH = "call_get_branch";
    private static final String CALL_BRANCH_DELTE_API = "call_branch_delete_api";
    @BindView(R.id.rv_branch_list)
    RecyclerView rv_branch_list;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_branch_list, container, false);
        setHasOptionsMenu(true);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        new GetWebServicesFragment(getActivity(), BranchListFragment.this, CALL_GET_BRANCH, false).execute(ApiConfig.GetURL);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Do something that differs the Activity's menu here
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_add_branch, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.menu_add:
                AddBranchHeadGFragment fragment = new AddBranchHeadGFragment();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_container, fragment);
                ft.addToBackStack(null);
                ft.commit();
                return false;
            default:
                break;
        }

        return false;
    }


    @Override
    public void onGetMsg(String[] msg) {
        String apicall = msg[0];
        String response = msg[1];
        switch (apicall) {
            case CALL_GET_BRANCH:
                parseGetBranches(response);
                break;
            case CALL_BRANCH_DELTE_API:
                parseBranchDelete(response);
                break;
        }
    }

    public void parseBranchDelete(String response){
        Log.d(TagUtils.getTag(),"branch delete response:-"+response);
        try{
            if(new JSONObject(response).optString("success").equals("true")){
                ToastClass.showShortToast(getActivity().getApplicationContext(),"branch Deleted");
                branchPOJOList.remove(branch_position);
                adapter.notifyDataSetChanged();
            }else{
                ToastClass.showShortToast(getActivity().getApplicationContext(),"Failed to delete branch");
            }
        }catch (Exception e){
            e.printStackTrace();
            ToastClass.showShortToast(getActivity().getApplicationContext(),"Server Down");
        }
    }
    BranchAdapte adapter;
    List<BranchPOJO> branchPOJOList=new ArrayList<>();
    public void parseGetBranches(String response) {
        Log.d(TagUtils.getTag(), "response:-" + response);
        branchPOJOList.clear();
        try {
            JSONArray jsonArray = new JSONArray(response);
            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonObject = jsonArray.optJSONObject(i);
                BranchPOJO branchPOJO = new BranchPOJO(jsonObject.optString("branch_id"),
                        jsonObject.optString("branch_name"),
                        jsonObject.optString("branch_code"),
                        jsonObject.optString("branch_status"),
                        jsonObject.optString("mobile"),
                        jsonObject.optString("phone"),
                        jsonObject.optString("email"),
                        jsonObject.optString("address"),
                        jsonObject.optString("city"),
                        jsonObject.optString("state"),
                        jsonObject.optString("country"),
                        jsonObject.optString("pincode"),
                        jsonObject.optString("website")
                );
                branchPOJOList.add(branchPOJO);
            }

            adapter = new BranchAdapte(getActivity(),this, branchPOJOList);
            LinearLayoutManager horizontalLayoutManagaer
                    = new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, false);
            rv_branch_list.setLayoutManager(horizontalLayoutManagaer);
            rv_branch_list.setHasFixedSize(true);
            rv_branch_list.setItemAnimator(new DefaultItemAnimator());
            rv_branch_list.setAdapter(adapter);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    int branch_position;
    public void callBranchDeleteAPI(BranchPOJO branchPOJO,int position){
        branch_position=position;
        ArrayList<NameValuePair> nameValuePairArrayList=new ArrayList<>();
        nameValuePairArrayList.add(new BasicNameValuePair("branch_code",branchPOJO.getBranch_code()));
        new WebServiceBaseFragment(nameValuePairArrayList,getActivity(),this,CALL_BRANCH_DELTE_API).execute(ApiConfig.DELETE_BRANCH_API);
    }

    public void callUpdateBranch(BranchPOJO branchPOJO){
        Intent i = new Intent(getActivity(), BranchUpdateFragment.class);
        i.putExtra("branchpojo",branchPOJO);
        startActivityForResult(i, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                String result=data.getStringExtra("result");
                new GetWebServicesFragment(getActivity(), BranchListFragment.this, CALL_GET_BRANCH, false).execute(ApiConfig.GetURL);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }
}
