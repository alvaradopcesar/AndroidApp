package iosco.app.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import iosco.app.R;
import iosco.app.model.entity.UserInfoEntity;
import iosco.app.service.ApiImplementation;
import iosco.app.utils.Helpers;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private static ProfileFragment instance;

    private UserInfoEntity userInfo;

    public static ProfileFragment getInstance(){
        if(instance == null){
            instance = new ProfileFragment();
        }
        return instance;
    }

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance(UserInfoEntity userInfoX) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("user", userInfoX);
        fragment.setArguments(bundle);

        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        userInfo = (UserInfoEntity) getArguments().getSerializable("user");

        Helpers.changeFont(getActivity(), ((TextView) rootView.findViewById(R.id.lblProfileName)), "HelveticaNeueLTStd-Roman.otf");
        Helpers.changeFont(getActivity(), ((TextView) rootView.findViewById(R.id.lblProfileJurisdiction)), "HelveticaNeueLTStd-Roman.otf");
        Helpers.changeFont(getActivity(), ((TextView) rootView.findViewById(R.id.lblProfileOrganization)), "HelveticaNeueLTStd-Roman.otf");
        Helpers.changeFont(getActivity(), ((TextView) rootView.findViewById(R.id.lblProfileJobTitle)), "HelveticaNeueLTStd-Roman.otf");

        Helpers.changeFont(getActivity(), ((TextView) rootView.findViewById(R.id.lblProfilePay)), "HelveticaNeueLTStd-Roman.otf");
        Helpers.changeFont(getActivity(), ((TextView) rootView.findViewById(R.id.lblProfileHotel)), "HelveticaNeueLTStd-Roman.otf");
        Helpers.changeFont(getActivity(), ((TextView) rootView.findViewById(R.id.lblProfileFlight)), "HelveticaNeueLTStd-Roman.otf");

        Helpers.changeFont(getActivity(), ((TextView) rootView.findViewById(R.id.txtProfileName)), "HelveticaNeueLTStd-Roman.otf");
        Helpers.changeFont(getActivity(), ((TextView) rootView.findViewById(R.id.txtProfileJurisdiction)), "HelveticaNeueLTStd-Roman.otf");
        Helpers.changeFont(getActivity(), ((TextView) rootView.findViewById(R.id.txtProfileOrganization)), "HelveticaNeueLTStd-Roman.otf");
        Helpers.changeFont(getActivity(), ((TextView) rootView.findViewById(R.id.txtProfileJobTitle)), "HelveticaNeueLTStd-Roman.otf");

        Helpers.changeFont(getActivity(), ((TextView) rootView.findViewById(R.id.txtProfilePay)), "HelveticaNeueLTStd-Roman.otf");
        Helpers.changeFont(getActivity(), ((TextView) rootView.findViewById(R.id.txtProfileHotel)), "HelveticaNeueLTStd-Roman.otf");
        Helpers.changeFont(getActivity(), ((TextView) rootView.findViewById(R.id.txtProfileFlight)), "HelveticaNeueLTStd-Roman.otf");


if(userInfo!=null) {

    ((TextView) rootView.findViewById(R.id.txtProfileName)).setText(userInfo.getFirstName() + " " + userInfo.getLastName());
    ((TextView) rootView.findViewById(R.id.txtProfileJurisdiction)).setText(userInfo.getOrganization().getJurisdiction().getName());
    ((TextView) rootView.findViewById(R.id.txtProfileOrganization)).setText(userInfo.getOrganization().getName());
    ((TextView) rootView.findViewById(R.id.txtProfileJobTitle)).setText(userInfo.getJobTitle());
    ((TextView) rootView.findViewById(R.id.txtProfilePay)).setText(!userInfo.isPendingPay() ? "Paid" : "Not paid");
    ((TextView) rootView.findViewById(R.id.txtProfileHotel)).setText(userInfo.getAccommodation().getName());
    ((TextView) rootView.findViewById(R.id.txtProfileFlight)).setText(userInfo.getFlight().getFlightNumberIn());


    ImageView ivPaymentStatus = (ImageView) rootView.findViewById(R.id.iv_pay_status);

    if (userInfo.isPendingPay()) {
        ivPaymentStatus.setImageResource(R.drawable.ic_state_1_off);
    } else {
        ivPaymentStatus.setImageResource(R.drawable.ic_state_1);
    }


    LinearLayout tempLay = null;
    if (!userInfo.isPayInfoVisible()) {
        tempLay = (LinearLayout) rootView.findViewById(R.id.ll_acm_info);
        tempLay.setVisibility(View.GONE);
    }
    if (!userInfo.isAccomodationInfoVisible()) {
        tempLay = (LinearLayout) rootView.findViewById(R.id.ll_acm_info);
        tempLay.setVisibility(View.GONE);
    }
    if (!userInfo.isFlightInfoVisible()) {
        tempLay = (LinearLayout) rootView.findViewById(R.id.ll_fly_info);
        tempLay.setVisibility(View.GONE);
    }


    if (userInfo.isHasProfilePicture()) {
        ApiImplementation.configImageLoader(getActivity());

        ImageLoader.getInstance().displayImage(ApiImplementation.getBaseUrl() + "api/Account/ProfilePicture", ((ImageView) rootView.findViewById(R.id.imgProfilePhoto)));
    }
}
        return rootView;
    }

}
