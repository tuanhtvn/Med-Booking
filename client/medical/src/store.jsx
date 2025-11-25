import { combineReducers } from "redux";
import { configureStore } from "@reduxjs/toolkit";

import {
  authReducer,
  forgotPasswordReducer,
  verifyCodeReducer,
  updatePasswordReducer,
} from "./reducers/userReducer";
import { doctorReducer, scheduledReducer } from "./reducers/doctorReducer";
import { recordReducer } from "./reducers/recordReducer";
import { ticketReducer, ticketsReducer } from "./reducers/ticketReducer";
import { chatReducer } from "./reducers/chatReducer";
const rootReducer = combineReducers({
  // reducers
  auth: authReducer,
  forgotPassword: forgotPasswordReducer,
  verifyCode: verifyCodeReducer,
  updatePassword: updatePasswordReducer,
  //
  record: recordReducer,
  doctor: doctorReducer,
  scheduled: scheduledReducer,
  ticket: ticketReducer,
  tickets: ticketsReducer,
  //
  chat: chatReducer,
});

const store = configureStore({
  reducer: rootReducer,
});

export default store;
