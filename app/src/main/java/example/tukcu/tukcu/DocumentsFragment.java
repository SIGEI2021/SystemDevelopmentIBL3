package example.tukcu.tukcu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.barteksc.pdfviewer.PDFView;
import com.tukcu.tukcu.R;

public class DocumentsFragment extends Fragment {

    private PDFView pdfView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View RootView =  inflater.inflate(R.layout.fragment_documents,container,false);

        pdfView = RootView.findViewById(R.id.pdfView);
        pdfView.fromAsset("cuconstitution.pdf").load();

        return RootView;
    }
}
