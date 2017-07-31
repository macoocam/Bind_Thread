# Bind_Thread
實作Bind和HandlerThread

# butterknife的官網  
http://jakewharton.github.io/butterknife/  
最下面Downloads的地方  
# 在build.gradle(Module:app)  
加入官網Download的字句  
並再onCreate的地方加入ButterKnife.bind(this)  
# 下載相關plugin，參考網址  
https://litotom.com/2016/07/22/butterknife/  
  
    
# HandlerThread相關code  
該程式會在textview重複顯示1~10，每一秒更新  

    public class MainActivity extends AppCompatActivity {

      @BindView(R.id.start)
      Button start;
      @BindView(R.id.textView)
      TextView textView;
      @BindView(R.id.stop)
      Button stop;

      private Handler handler;
      private HandlerThread mThread;

      @Override
      protected void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          setContentView(R.layout.activity_main);
          ButterKnife.bind(this);

          mThread = new HandlerThread("name");
          mThread.start();
          handler = new Handler(mThread.getLooper());
          handler.post(r1);
      }

      int i = 0;
      private Runnable r1 = new Runnable() {
          @Override
          public void run() {
              if (i < 10) {
                  runOnUiThread(new Runnable() {
                    @Override
                      public void run() {
                          textView.setText(i + "");
                      }
                  });
                  i++;
              } else {
                  i = 0;
              }
              handler.postDelayed(this,1000);
          }
      };

      @OnClick({R.id.start, R.id.textView, R.id.stop})
      public void onViewClicked(View view) {
          switch (view.getId()) {
              case R.id.start:
                  mThread = new HandlerThread("name");
                  mThread.start();
                  handler = new Handler(mThread.getLooper());
                  handler.post(r1);
                  break;
              case R.id.stop:
                  textView.setText("stop");
                  handler.removeCallbacks(r1);
                  mThread.quit();
                  break;
          }
      }
    }
