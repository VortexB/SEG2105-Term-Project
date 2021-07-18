package com.example.termproject;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class StudentFragmentAdapter extends FragmentStateAdapter {
    String studentName;
    public StudentFragmentAdapter(FragmentManager fragmentManager, Lifecycle lifecycle, String studentName) {
        super(fragmentManager, lifecycle);
        this.studentName=studentName;
    }

    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 1:
                return new StudentMyClassesFragment(studentName);
            default:
                return new StudentAllClassesFragment(studentName);

        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }

}
