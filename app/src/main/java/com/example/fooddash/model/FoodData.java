package com.example.fooddash.model;


import java.util.List;

    public class FoodData {

        private List<Popular> popular = null;
        private List<Recommended> recommended = null;
        private List<Menu> menu = null;

        public List<Popular> getPopular() {
            return popular;
        }

        public void setPopular(List<Popular> popular) {
            this.popular = popular;
        }

        public List<Recommended> getRecommended() {
            return recommended;
        }

        public void setRecommended(List<Recommended> recommended) {
            this.recommended = recommended;
        }

        public List<Menu> getMenu() {
            return menu;
        }

        public void setMenu(List<Menu> menu) {
            this.menu = menu;
        }

    }


