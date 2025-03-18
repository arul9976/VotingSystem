import { configureStore } from "@reduxjs/toolkit"
import authReducer from "./features/auth/authSlice"
import electionReducer from "./features/elections/electionSlice"

export const store = configureStore({
  reducer: {
    auth: authReducer,
    electionState: electionReducer,
  },
})

