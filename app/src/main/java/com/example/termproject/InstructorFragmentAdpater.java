package com.example.termproject;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class InstructorFragmentAdpater extends FragmentStateAdapter {
    String instructorName;
    public InstructorFragmentAdpater(FragmentManager fragmentManager, Lifecycle lifecycle,String instructorName) {
        super(fragmentManager, lifecycle);
        this.instructorName=instructorName;
    }

    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 1:
                return new InstructorMyClassesFragment(instructorName);
            default:
                return new InstructorAllClassesFragment(instructorName);

        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }


}
