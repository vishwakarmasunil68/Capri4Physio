package com.capri4physio.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.capri4physio.R;
import com.capri4physio.util.ImageUtil;
import com.isseiaoki.simplecropview.CropImageView;


public class CropActivity extends BaseActivity {

	private Uri mImageCaptureUri;
	private Button mCancel,mDone;
	public static Bitmap croppedImage;
	private CropImageView mCropImageView;
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_crop);

		mCropImageView = (CropImageView) findViewById(R.id.cropImageView);
		String _path=getIntent().getExtras().getString("url");

		int orientation = ImageUtil.getExifOrientation(_path);
		BitmapFactory.Options resample = new BitmapFactory.Options();
		resample.inSampleSize = 6;
		Bitmap	bmpImage = BitmapFactory.decodeFile(_path,resample);

		if(orientation == 90){
			bmpImage = ImageUtil.rotate(bmpImage, 90);
		} else if(orientation == 180){
			bmpImage = ImageUtil.rotate(bmpImage, 180);
		}else if(orientation == 270){
			bmpImage = ImageUtil.rotate(bmpImage, 270);
		}


		mCropImageView.setImageBitmap(bmpImage);
		mCropImageView.setCropMode(CropImageView.CropMode.RATIO_1_1);
		mCropImageView.setMinFrameSizeInDp(130);
		mCancel = (Button) findViewById(R.id.cancel);
		mCancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

		mDone= (Button) findViewById(R.id.done);
		mDone.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				croppedImage =mCropImageView.getCroppedBitmap();
				Intent mIntent = new Intent();
				setResult(RESULT_OK,mIntent);
				finish();
			}
		});

	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		if (mImageCaptureUri != null) {
			outState.putString("cameraImageUri", mImageCaptureUri.toString());
		}
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		if (savedInstanceState.containsKey("cameraImageUri")) {
			mImageCaptureUri = Uri.parse(savedInstanceState.getString("cameraImageUri"));
		}
	}

}
