package farmer.zpm.com.zxingdemo;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.zpm.zxinglib.EncoderUtil;

public class MainActivity extends AppCompatActivity {
    private Button btn=null;
    private ImageView image=null;
    private EditText editText=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn=(Button)findViewById(R.id.btn);
        image=(ImageView)findViewById(R.id.image);
        editText=(EditText)findViewById(R.id.edittext);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String content = editText.getText().toString();
                //判断内容是否为空
                image.setImageBitmap( EncoderUtil.getStringQRcodeWithoutLogo(content, 200, 200));;
                image.setImageBitmap( EncoderUtil.getStringQRcodeWithLogo(content, 200, 200, BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher)));;
            }
        });
        findViewById(R.id.scan).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent=new Intent(MainActivity.this,ScannerActivity.class);
                startActivity(intent);
            }
        });
    }
}
