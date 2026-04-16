import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      redirect: '/seats'
    },
    {
      path: '/seats',
      component: () => import('../views/FloorSeatView.vue'),
    },
  ],
})

export default router
