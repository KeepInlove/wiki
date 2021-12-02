import { createStore } from 'vuex'
//定义SessionStorage
declare let SessionStorage: any;
const USER = "USER";

const  store= createStore({
  state: {
    //缓存里面取值
    user: SessionStorage.get(USER) || {}
  },
  mutations: {
    setUser (state, user) {
      console.log("store user：", user);
      state.user = user;
      //缓存中she'z
      SessionStorage.set(USER, user);
    }
  },
  actions: {
  },
  modules: {
  }
});
export default store;
