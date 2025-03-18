import { createSlice } from "@reduxjs/toolkit"

const initialState = {
  elections: {},
  candidates: [],
  parties: [],
  loading: false,
  error: null,
}

const electionSlice = createSlice({
  name: "electionState",
  initialState,
  reducers: {
    setElections: (state, action) => {
      state.elections[action.payload[0].status] = action.payload;

    },
    addElection: (state, action) => {
      state.elections.push(action.payload)
    },
    updateElection: (state, action) => {
      const index = state.elections.findIndex((e) => e.id === action.payload.id)
      if (index !== -1) {
        state.elections[index] = action.payload
      }
    },
    deleteElection: (state, action) => {
      state.elections = state.elections.filter((e) => e.id !== action.payload)
    },
    setCandidates: (state, action) => {
      state.candidates = action.payload
    },
    approveCandidate: (state, action) => {
      const index = state.candidates.findIndex((c) => c.id === action.payload)
      if (index !== -1) {
        state.candidates[index].approved = true
      }
    },
    setParties: (state, action) => {
      state.parties = action.payload
    },
    setLoading: (state, action) => {
      state.loading = action.payload
    },
    setError: (state, action) => {
      state.error = action.payload
    },
  },
})

export const {
  setElections,
  addElection,
  updateElection,
  deleteElection,
  setCandidates,
  approveCandidate,
  setParties,
  setLoading,
  setError,
} = electionSlice.actions

export default electionSlice.reducer

