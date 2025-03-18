import { createSlice } from "@reduxjs/toolkit"

const initialState = {
  isAuthenticated: false,
  userRole: null,
  email: null,
}

const authSlice = createSlice({
  name: "auth",
  initialState,
  reducers: {
    login: (state, action) => {
      state.isAuthenticated = true
      state.email = action.payload.email
      state.userRole = action.payload.role
    },
    logout: (state, action) => {
      state.isAuthenticated = false
      state.email = null
      state.userRole = null
    },
  },
})

export const { login, logout } = authSlice.actions
export default authSlice.reducer

