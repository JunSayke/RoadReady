package com.example.roadready.fragments.dealership.mainnavfragments;

    import android.os.Bundle;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.ArrayAdapter;
	import android.widget.Toast;

	import androidx.annotation.NonNull;
	import androidx.annotation.Nullable;
	import androidx.fragment.app.Fragment;

	import com.example.roadready.classes.general.MainFacade;
	import com.example.roadready.databinding.FragmentDealershipLtoBinding;

public class Lto_Fragment extends Fragment {
		private final String TAG = "Lto_Fragment"; // declare TAG for each class for debugging purposes using Log.d()
		private FragmentDealershipLtoBinding binding; // use View binding to avoid using too much findViewById
		private MainFacade mainFacade;

		public View onCreateView(@NonNull LayoutInflater inflater,
								 ViewGroup container, Bundle savedInstanceState) {

			binding = FragmentDealershipLtoBinding.inflate(inflater, container, false);
			View root = binding.getRoot();

			try{
				mainFacade = MainFacade.getInstance();
			} catch (Exception e) {
                throw new RuntimeException(e);
            }

            String[] items = new String[]{"Office Location", "Mandaue", "Robinsons Galleria"};
			ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getContext(), android.R.layout.simple_spinner_dropdown_item, items);
			binding.rdSpinnerSubmitLto.setAdapter(adapter);

			return root;
		}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		binding.rdBtnSubmit.setOnClickListener(v -> {
			mainFacade.makeToast("Coming Soon", Toast.LENGTH_SHORT);
		});
	}

	@Override
		public void onDestroyView() {
			super.onDestroyView();
			binding = null;
		}

}
	
	