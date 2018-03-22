package gie.gazz.mefeeder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    public FirebaseDatabase firebaseDatabase; //การติดต่อแบบ Database
    public DatabaseReference Servor; //รับตัวแปรจาก Database
    private static final String TAG = "Servor";
    public Button Switch; //กำหนด Button
    public Integer Value,Value_refer;//ตัวแปรรับค่าและส่งค่า

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseDatabase =  FirebaseDatabase.getInstance();
        Servor = firebaseDatabase.getReference("Servor");//รับตัวแปรจาก Database จากส่วนไหน

        Switch = (Button)findViewById(R.id.btSwitch);//กำหนด Button

        //ส่วนของการกำหนดค่า รับและส่ง
        Servor.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Value = dataSnapshot.getValue(Integer.class);
                if (Value == 1){//ตรวจสอบค่า Value ว่าเท่ากับ 1
                    Switch.setText("Feeding");//เปรียนข้อความบน Switch
                    Value_refer = 0;//เปลี่ยนค่าส่งกลับ
                }else {
                    Switch.setText("Feed");//เปรียนข้อความบน Switch
                    Value_refer = 1;//เปลี่ยนค่าส่งกลับ
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {//หากค่า Value ส่งไม่ผ่าน
                Log.w(TAG, "Failed to read value.", databaseError.toException());//แสดงข้อความ Error ผิดพลาด
            }
        });

        Switch.setOnClickListener(new View.OnClickListener() {//เมื่อกด Switch
            @Override
            public void onClick(View view) {
                Servor.setValue(Value_refer);//ส่งค่ากลับไปที่ตัวแปรบน Firebase
            }
        });

    }
}
