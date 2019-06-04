/*
 * Copyright 2015, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.testing.espresso.BasicSample;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/**
 * An {@link Activity} that gets a text string from the user and displays it back when the user
 * clicks on one of the two buttons. The first one shows it in the same activity and the second
 * one opens another activity and displays the message.
 */
public class MainActivity extends Activity implements View.OnClickListener {

    // The TextView used to display the message inside the Activity.
    private TextView mTextView;

    // The EditText where the user types the message.
    private EditText mEditText;
    private static final int REQUEST_CODE_PICK = 16;
    private EditText mCallerNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Check intent of Test
        mCallerNumber = findViewById(R.id.edit_text_caller_number);


        // Set the listeners for the buttons.
        findViewById(R.id.btnChangeText).setOnClickListener(this);
        findViewById(R.id.activityChangeTextBtn).setOnClickListener(this);

        mTextView = findViewById(R.id.textToBeChanged);
        mEditText = findViewById(R.id.editTextUserInput);

        final TextView labelTextView = findViewById(R.id.textToBeChangedFromSpinner);
        Spinner spinner = findViewById(R.id.spinner_countries);

        spinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.europe_countries)));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    labelTextView.setText((CharSequence) parent.getSelectedItem());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    public void onCall(View view) {
        boolean hasCallPhonePermission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED;

        if (hasCallPhonePermission)
            startActivity(createCallIntentFromNumber());
        else
            Toast.makeText(this, R.string.warning_call_phone_permission, Toast.LENGTH_SHORT).show();
    }

    public void onPickContact(View view) {
        final Intent pickContactIntent = new Intent(this, ContactsActivity.class);
        startActivityForResult(pickContactIntent, REQUEST_CODE_PICK);
    }

    private Intent createCallIntentFromNumber() {
        final Intent intentToCall = new Intent(Intent.ACTION_CALL);
        String number = mCallerNumber.getText().toString();
        intentToCall.setData(Uri.parse("tel:" + number));
        return intentToCall;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_PICK) {
            if (resultCode == RESULT_OK) {
                mCallerNumber.setText(data.getExtras()
                        .getString(ContactsActivity.KEY_PHONE_NUMBER));
            }
        }
    }
    @Override
    public void onClick(View view) {
        // Get the text from the EditText view.
        final String text = mEditText.getText().toString();

        final int changeTextBtId = R.id.btnChangeText;
        final int activityChangeTextBtnId = R.id.activityChangeTextBtn;

        if (view.getId() == changeTextBtId) {
          // First button's interaction: set a text in a text view.
          mTextView.setText(text);
        } else if (view.getId() == activityChangeTextBtnId) {
          // Second button's interaction: start an activity and send a message to it.
          Intent intent = com.example.android.testing.espresso.BasicSample.ShowTextActivity
              .newStartIntent(this, text);
          startActivity(intent);
        }
    }
}
