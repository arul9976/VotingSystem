
import { useState } from "react"
import { Modal } from "./Modal"
import { Button } from "./Button"
import { getCandidates } from "../Fetch/voterFetch"
import { setCandidates } from "../lib/features/elections/electionSlice"

export function ElectionDetailsModal({ isOpen, onClose, election, dispatch, candidates, activeTab, setActiveTab }) {
  if (!election) return null


  const isOngoing = election.status === "ONGOING"
  const isUpcoming = election.status === "UPCOMING"
  const isCompleted = election.status === "ENDED"


  const handleCandidatesClick = () => {
    setActiveTab("candidates");
    
    getCandidates(election.electionId)
      .then(res => {
        if (res && res.length > 0) {
          dispatch(setCandidates(res))
        }
      }).catch(error => {
        console.error("Error fetching candidates");
      })
  }


  return (
    <Modal
      isOpen={isOpen}
      onClose={onClose}
      title={election.electionName}
      footer={
        <div className="flex justify-end w-full gap-2">
          <Button variant="outline" onClick={onClose}>
            Close
          </Button>
          {isOngoing && <Button>Cast Vote</Button>}
          {isUpcoming && <Button>Register</Button>}
        </div>
      }
    >
      <div className="space-y-6">
        <div className="flex border-b">
          <button
            className={`px-4 py-2 font-medium text-sm ${activeTab === "overview" ? "border-b-2 border-primary text-primary" : "text-muted-foreground"
              }`}
            onClick={() => setActiveTab("overview")}
          >
            Overview
          </button>
          <button
            className={`px-4 py-2 font-medium text-sm ${activeTab === "candidates" ? "border-b-2 border-primary text-primary" : "text-muted-foreground"
              }`}
            onClick={handleCandidatesClick}
          >
            Candidates
          </button>
          {(isOngoing || isCompleted) && (
            <button
              className={`px-4 py-2 font-medium text-sm ${activeTab === "results" ? "border-b-2 border-primary text-primary" : "text-muted-foreground"
                }`}
              onClick={() => setActiveTab("results")}
            >
              Results
            </button>
          )}
        </div>

        {activeTab === "overview" && (
          <div className="space-y-4 animate-in fade-in-50">
            <div>
              <h3 className="font-medium text-sm text-muted-foreground">Description</h3>
              <p className="mt-1">{election.description || "No description available."}</p>
            </div>

            <div className="grid grid-cols-2 gap-4">
              <div>
                <h3 className="font-medium text-sm text-muted-foreground">Status</h3>
                <p className="mt-1">
                  <span
                    className={`px-2 py-1 rounded-full text-xs font-medium ${isOngoing
                      ? "bg-green-100 text-green-800 dark:bg-green-800/20 dark:text-green-400"
                      : isUpcoming
                        ? "bg-amber-100 text-amber-800 dark:bg-amber-800/20 dark:text-amber-400"
                        : "bg-blue-100 text-blue-800 dark:bg-blue-800/20 dark:text-blue-400"
                      }`}
                  >
                    {isOngoing ? "Active" : isUpcoming ? "Upcoming" : "Completed"}
                  </span>
                </p>
              </div>

              <div>
                <h3 className="font-medium text-sm text-muted-foreground">Total Candidates</h3>
                <p className="mt-1">{election.totalCandidates || 0}</p>
              </div>

              {(isOngoing || isCompleted) && (
                <div>
                  <h3 className="font-medium text-sm text-muted-foreground">Total Votes</h3>
                  <p className="mt-1">{election.totalVotes || 0}</p>
                </div>
              )}

              {isUpcoming && (
                <div>
                  <h3 className="font-medium text-sm text-muted-foreground">Starts On</h3>
                  <p className="mt-1">{new Date(election.startDate).toLocaleDateString()}</p>
                </div>
              )}

              {isOngoing && (
                <div>
                  <h3 className="font-medium text-sm text-muted-foreground">Ends On</h3>
                  <p className="mt-1">{new Date(election.endDate).toLocaleDateString()}</p>
                </div>
              )}

              {isCompleted && (
                <div>
                  <h3 className="font-medium text-sm text-muted-foreground">Completed On</h3>
                  <p className="mt-1">{new Date(election.endDate).toLocaleDateString()}</p>
                </div>
              )}
            </div>

            {isOngoing && (
              <div>
                <h3 className="font-medium text-sm text-muted-foreground">Your Status</h3>
                <p className="mt-1 text-green-500 font-medium">Voted</p>
              </div>
            )}

            {isCompleted && (
              <div>
                <h3 className="font-medium text-sm text-muted-foreground">Winner</h3>
                <p className="mt-1 font-medium">{election.winner}</p>
              </div>
            )}
          </div>
        )}

        {(activeTab === "candidates") && (
          <div className="space-y-4 animate-in fade-in-50">
            {candidates.length > 0 ? candidates.map((candidate, i) => (
              <div key={i} className="flex items-center p-3 rounded-lg border">
                <div className="w-12 h-12 rounded-full bg-muted flex items-center justify-center mr-4">
                  <svg
                    xmlns="http://www.w3.org/2000/svg"
                    width="24"
                    height="24"
                    viewBox="0 0 24 24"
                    fill="none"
                    stroke="currentColor"
                    strokeWidth="2"
                    strokeLinecap="round"
                    strokeLinejoin="round"
                    className="h-6 w-6 text-muted-foreground"
                  >
                    <path d="M19 21v-2a4 4 0 0 0-4-4H9a4 4 0 0 0-4 4v2"></path>
                    <circle cx="12" cy="7" r="4"></circle>
                  </svg>
                </div>
                <div className="flex-1">
                  <h4 className="font-medium">{candidate.candidateName}</h4>
                  <p className="text-sm text-muted-foreground">{candidate.party}</p>
                </div>
                {isOngoing && (
                  <div className="ml-4">
                    <Button variant="outline" size="sm">
                      Vote
                    </Button>
                  </div>
                )}
                {isCompleted && (
                  <div className="ml-4 text-right">
                    <div className="font-medium">{Math.floor(Math.random() * 100)}%</div>
                    <div className="text-sm text-muted-foreground">{Math.floor(Math.random() * 1000)} votes</div>
                  </div>
                )}
              </div>
            )) : <h1>No Candidates Found</h1>}
          </div>
        )}

        {activeTab === "results" && (
          <div className="space-y-4 animate-in fade-in-50">
            <div className="space-y-3">
              {[1, 2, 3, 4, 5].slice(0, election.candidates || 3).map((i) => {
                const percentage = isCompleted
                  ? i === 1
                    ? 45
                    : i === 2
                      ? 30
                      : 25 - i * 3
                  : Math.floor(Math.random() * 100)

                return (
                  <div key={i} className="space-y-1">
                    <div className="flex justify-between text-sm">
                      <span>Candidate {i}</span>
                      <span>{percentage}%</span>
                    </div>
                    <div className="w-full bg-muted rounded-full h-2.5">
                      <div className="bg-primary h-2.5 rounded-full" style={{ width: `${percentage}%` }}></div>
                    </div>
                    <div className="text-xs text-muted-foreground">{Math.floor(percentage * 10)} votes</div>
                  </div>
                )
              })}
            </div>

            <div className="pt-4 border-t">
              <h3 className="font-medium mb-2">Statistics</h3>
              <div className="grid grid-cols-2 gap-4">
                <div>
                  <p className="text-sm text-muted-foreground">Total Eligible Voters</p>
                  <p className="font-medium">{Math.floor(Math.random() * 5000) + 1000}</p>
                </div>
                <div>
                  <p className="text-sm text-muted-foreground">Voter Turnout</p>
                  <p className="font-medium">{Math.floor(Math.random() * 70) + 30}%</p>
                </div>
              </div>
            </div>
          </div>
        )}
      </div>
    </Modal>
  )
}

