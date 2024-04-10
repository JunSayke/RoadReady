package com.example.roadready.fragments.dealership;

    import android.os.Bundle;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.ArrayAdapter;

    import androidx.annotation.NonNull;
    import androidx.fragment.app.Fragment;

	import com.example.roadready.databinding.FragmentDealershipLtoBinding;

public class Lto_Fragment extends Fragment {
		private final String TAG = "Lto_Fragment"; // declare TAG for each class for debugging purposes using Log.d()
		private FragmentDealershipLtoBinding binding; // use View binding to avoid using too much findViewById

		public View onCreateView(@NonNull LayoutInflater inflater,
								 ViewGroup container, Bundle savedInstanceState) {

			binding = FragmentDealershipLtoBinding.inflate(inflater, container, false);
			View root = binding.getRoot();

			//There is a much better way of implementing a dropdown list, but this is good for now
			String[] items = new String[]{"Office Location", "Mandaue", "Robinsons Galleria"};
			ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getContext(), android.R.layout.simple_spinner_dropdown_item, items);
			binding.rdSpinnerSubmitLto.setAdapter(adapter);

			return root;
		}

		@Override
		public void onDestroyView() {
			super.onDestroyView();
			binding = null;
		}

}
	
	