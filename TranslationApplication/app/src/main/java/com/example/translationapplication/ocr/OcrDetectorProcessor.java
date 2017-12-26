package com.example.translationapplication.ocr;

import android.content.Context;
import android.util.SparseArray;
import android.widget.Toast;

import com.example.translationapplication.home.MainModel;
import com.example.translationapplication.http.ServiceProvider;
import com.example.translationapplication.http.VolleyCallback;
import com.example.translationapplication.util.TranslationType;
import com.google.android.gms.internal.zzdke;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.text.TextBlock;

/**
 * Created by null on 10/17/17.
 */

public class OcrDetectorProcessor implements Detector.Processor<TextBlock> {

    private GraphicOverlay<OcrGraphic> mGraphicOverlay;
    private Context context;

    public OcrDetectorProcessor(GraphicOverlay<OcrGraphic> ocrGraphicOverlay) {
        mGraphicOverlay = ocrGraphicOverlay;
    }

    public OcrDetectorProcessor(GraphicOverlay<OcrGraphic> ocrGraphicOverlay, Context context) {
        this.mGraphicOverlay = ocrGraphicOverlay;
        this.context = context;
    }

    /**
     * Called by the detector to deliver detection results.
     * If your application called for it, this could be a place to check for
     * equivalent detections by tracking TextBlocks that are similar in location and content from
     * previous frames, or reduce noise by eliminating TextBlocks that have not persisted through
     * multiple detections.
     */
    @Override
    public void receiveDetections(Detector.Detections<TextBlock> detections) {
        mGraphicOverlay.clear();
        SparseArray<TextBlock> items = detections.getDetectedItems();
        for (int i = 0; i < items.size(); ++i) {
            ServiceProvider.newInstance().requestPapagoAPI(
                    context,
                    TranslationType.SMT,
                    new VolleyCallback() {
                        @Override
                        public void onSuccess(MainModel result) {
                            Toast.makeText(context, result.toString(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFail() {
                            Toast.makeText(context, "RequestFailed", Toast.LENGTH_SHORT).show();
                        }
                    },
                    items.get(i).getValue());
            TextBlock item = items.valueAt(i);
            OcrGraphic graphic = new OcrGraphic(mGraphicOverlay, item);
            mGraphicOverlay.add(graphic);
        }
    }

    /**
     * Frees the resources associated with this detection processor.
     */
    @Override
    public void release() {
        mGraphicOverlay.clear();
    }
}
