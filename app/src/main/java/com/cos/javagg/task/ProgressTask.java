package com.cos.javagg.task;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.cos.javagg.SearchResultActivity;

public class ProgressTask /*extends AsyncTask<Integer, Integer, Void>*/ {

//    private ProgressDialog progressDialog = null;// 원형 ProgressBar 생성
//    private SearchResultActivity searchResultActivity;
//    public ProgressTask(SearchResultActivity searchResultActivity) {
//        this.searchResultActivity = searchResultActivity;
//    }
//
//    @Override
//    // doInBackground 전에 실행(UI Thread) - 백그라운드 작업 전 초기화 부분
//    protected void onPreExecute() {
//        super.onPreExecute();
//        // ProgressDialog 생성, 레이아웃 변경
//        progressDialog = new ProgressDialog(this.searchResultActivity, android.R.style.Theme_Material_Dialog_Alert);
//        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);      // Style - 원 모양 설정
//        progressDialog.setMessage("소환사 정보를 불러오는중 입니다.");                           // Message - 표시할 텍스트
//        progressDialog.setCanceledOnTouchOutside(false);                    // 터치시 Canceled 막기
//        progressDialog.show();                                              // UI 표시
//    }
//
//    @Override
//    // 백그라운드 작업 시작, UI 조작 불가, onPreExcute() 종료후 바로 호출
//    protected Void doInBackground(Integer... ints) {
//        for (int i = 0; i < 4; i++) {
//            try {
//                // UI Update, publishProgress() - onProgressUpdate 호출
//                publishProgress(ints[0]);
//                Thread.sleep(500);                  // 0.5초 간격 UI Update
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//        return null;
//    }
//
//    @Override
//    // UI 조작가능 (UI Thread에서 실행)
//    protected void onProgressUpdate(Integer... values) {
//        super.onProgressUpdate(values);
//        progressBar.incrementProgressBy(values[0]);
//        progress_value.setText(progressBar.getProgress()+"%");
//    }
//
//    @Override
//    // UI Thread에서 실행, doInBackground 종료 후 바로 호출
//    protected void onPostExecute(Void aVoid) {
//        super.onPostExecute(aVoid);
//        progressDialog.dismiss();       // ProgressDialog 지우기
//        progressBar.setProgress(20);
//        progress_value.setText(progressBar.getProgress()+"%");
//    }
}
