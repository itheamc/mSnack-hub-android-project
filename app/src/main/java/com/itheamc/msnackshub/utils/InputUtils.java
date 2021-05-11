package com.itheamc.msnackshub.utils;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;

import androidx.viewbinding.ViewBinding;

import com.itheamc.msnackshub.databinding.VerificationBottomSheetViewBinding;

public class InputUtils {

    /**
     * _______________________________________________________________________________
    This is the function to check whether phone number is entered or not and to verify
    that it is in right format
     * _______________________________________________________________________________
     */
    public static boolean isNumberValidated(String phone) {
        boolean isValidated = false;
        if (!isPhoneNumberEmpty(phone)) {
            if (!isPhoneNumberContainsUnsupportedCharacters(phone)) {
                if (isValidNumber(phone)) {
                    isValidated = true;
                }
            }
        }

        return isValidated;
    }

    // Checking whether number is empty or not
    private static boolean isPhoneNumberEmpty(String s) {
        return TextUtils.isEmpty(s.trim());
    }

    // Checking whether number contains unsupported characters or not
    private static boolean isPhoneNumberContainsUnsupportedCharacters(String s) {
        s = s.trim();
        return s.contains(" ") ||
                s.contains("-") ||
                s.contains("+") ||
                s.contains("*") ||
                s.contains("#") ||
                s.contains(";") ||
                s.contains(".") ||
                s.contains(",") ||
                s.contains("N") ||
                s.contains("/") ||
                s.contains(")") ||
                s.contains("(");
    }

    // Checking whether number start with correct format or not
    private static boolean isValidNumber(String s) {
        return s.trim().length() == 10 && s.trim().startsWith("98");
    }

    /**
     * ______________________________________________________________
     * This is the function to return the formatted verification code
     * ______________________________________________________________
     */
    public static String formatVerificationCode(ViewBinding viewBinding) {
        VerificationBottomSheetViewBinding verificationBinding =
                ((VerificationBottomSheetViewBinding) viewBinding);
        String code1 = verificationBinding.verificationNumber1.getText().toString().trim();
        String code2 = verificationBinding.verificationNumber2.getText().toString().trim();
        String code3 = verificationBinding.verificationNumber3.getText().toString().trim();
        String code4 = verificationBinding.verificationNumber4.getText().toString().trim();
        String code5 = verificationBinding.verificationNumber5.getText().toString().trim();
        String code6 = verificationBinding.verificationNumber6.getText().toString().trim();

        String formattedCode = code1 + code2 + code3 + code4 + code5 + code6;

        if (formattedCode.length() != 6) {
            formattedCode = null;
        }

        return formattedCode;
    }



    // Function to change focuses on the otp edit text
    public static void requestFocusOnEditText(ViewBinding viewBinding) {
        VerificationBottomSheetViewBinding verificationBinding =
                ((VerificationBottomSheetViewBinding) viewBinding);

        String code1 = verificationBinding.verificationNumber1.getText().toString().trim();
        String code2 = verificationBinding.verificationNumber2.getText().toString().trim();
        String code3 = verificationBinding.verificationNumber3.getText().toString().trim();
        String code4 = verificationBinding.verificationNumber4.getText().toString().trim();
        String code5 = verificationBinding.verificationNumber5.getText().toString().trim();
        String code6 = verificationBinding.verificationNumber6.getText().toString().trim();

        verificationBinding.verificationNumber1.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!verificationBinding.verificationNumber1.getText().toString().trim().isEmpty()) {
                    verificationBinding.verificationNumber2.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        verificationBinding.verificationNumber2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!verificationBinding.verificationNumber2.getText().toString().trim().isEmpty()) {
                    verificationBinding.verificationNumber3.requestFocus();
                } else {
                    verificationBinding.verificationNumber1.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        verificationBinding.verificationNumber3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!verificationBinding.verificationNumber3.getText().toString().trim().isEmpty()) {
                    verificationBinding.verificationNumber4.requestFocus();
                } else {
                    verificationBinding.verificationNumber2.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        verificationBinding.verificationNumber4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!verificationBinding.verificationNumber4.getText().toString().trim().isEmpty()) {
                    verificationBinding.verificationNumber5.requestFocus();
                } else {
                    verificationBinding.verificationNumber3.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        verificationBinding.verificationNumber5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!verificationBinding.verificationNumber5.getText().toString().trim().isEmpty()) {
                    verificationBinding.verificationNumber6.requestFocus();
                } else {
                    verificationBinding.verificationNumber4.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        verificationBinding.verificationNumber6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (verificationBinding.verificationNumber6.getText().toString().trim().isEmpty()) {
                    verificationBinding.verificationNumber5.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }


}
