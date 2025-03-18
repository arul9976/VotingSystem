"use client"

import { useState, useEffect } from "react"
import { useNavigate } from "react-router-dom"
import { useSelector } from "react-redux"
import { Button } from "../components/Button"
import { Card, CardContent, CardDescription, CardFooter, CardHeader, CardTitle } from "../components/Card"
import { Input } from "../components/Input"
import { ElectionDetailsModal } from "../components/ElectionDetailsModal"

export default function Elections() {
  const navigate = useNavigate()
  const isAuthenticated = useSelector((state) => state.auth.isAuthenticated)
  const [searchTerm, setSearchTerm] = useState("")
  const [activeTab, setActiveTab] = useState("ongoing")
  const [selectedElection, setSelectedElection] = useState(null)
  const [isModalOpen, setIsModalOpen] = useState(false)

  useEffect(() => {
    if (!isAuthenticated) {
      navigate("/login")
    }
  }, [isAuthenticated, navigate])

  if (!isAuthenticated) {
    return null
  }

  const ongoingElections = [
    {
      id: 1,
      title: "City Council Election",
      description: "Election for the city council members who will represent the citizens for the next 4 years.",
      endDate: "2023-12-15",
      candidates: 5,
      votes: 120,
      status: "ongoing",
    },
    {
      id: 2,
      title: "School Board Election",
      description: "Election for the school board members who will oversee educational policies.",
      endDate: "2023-12-20",
      candidates: 4,
      votes: 85,
      status: "ongoing",
    },
    {
      id: 3,
      title: "Community Association",
      description: "Election for the community association leadership positions.",
      endDate: "2023-12-18",
      candidates: 3,
      votes: 65,
      status: "ongoing",
    },
  ]

  const upcomingElections = [
    {
      id: 4,
      title: "State Election 1",
      description: "Upcoming state election for various positions in the state government.",
      startDate: "2023-12-25",
      candidates: 4,
      status: "upcoming",
    },
    {
      id: 5,
      title: "State Election 2",
      description: "Second phase of the state elections focusing on legislative positions.",
      startDate: "2023-12-28",
      candidates: 5,
      status: "upcoming",
    },
    {
      id: 6,
      title: "State Election 3",
      description: "Third phase of the state elections for remaining positions.",
      startDate: "2024-01-05",
      candidates: 6,
      status: "upcoming",
    },
    {
      id: 7,
      title: "State Election 4",
      description: "Final phase of the state elections covering special positions.",
      startDate: "2024-01-12",
      candidates: 7,
      status: "upcoming",
    },
  ]

  const completedElections = [
    {
      id: 8,
      title: "National Election 1",
      description: "First national election of the year covering presidential and vice-presidential positions.",
      completedDate: "2023-11-05",
      winner: "Candidate 2",
      votes: 1230,
      candidates: 5,
      status: "completed",
    },
    {
      id: 9,
      title: "National Election 2",
      description: "Second national election covering senate positions.",
      completedDate: "2023-10-15",
      winner: "Candidate 3",
      votes: 1460,
      candidates: 4,
      status: "completed",
    },
    {
      id: 10,
      title: "National Election 3",
      description: "Third national election for house representatives.",
      completedDate: "2023-09-25",
      winner: "Candidate 4",
      votes: 1690,
      candidates: 6,
      status: "completed",
    },
    {
      id: 11,
      title: "National Election 4",
      description: "Special election for vacant positions in the government.",
      completedDate: "2023-08-05",
      winner: "Candidate 5",
      votes: 1920,
      candidates: 3,
      status: "completed",
    },
    {
      id: 12,
      title: "National Election 5",
      description: "Final national election of the previous year.",
      completedDate: "2023-07-15",
      winner: "Candidate 6",
      votes: 2150,
      candidates: 7,
      status: "completed",
    },
  ]

  const filteredOngoing = ongoingElections.filter((election) =>
    election.title.toLowerCase().includes(searchTerm.toLowerCase()),
  )

  const filteredUpcoming = upcomingElections.filter((election) =>
    election.title.toLowerCase().includes(searchTerm.toLowerCase()),
  )

  const filteredCompleted = completedElections.filter((election) =>
    election.title.toLowerCase().includes(searchTerm.toLowerCase()),
  )

  const openElectionDetails = (election) => {
    setSelectedElection(election)
    setIsModalOpen(true)
  }

  const closeElectionDetails = () => {
    setIsModalOpen(false)
  }

  return (
    <div className="container py-8 animate-in fade-in-50 duration-500">
      <h1 className="text-3xl font-bold mb-6">Elections</h1>

      <div className="relative mb-6">
        <div className="absolute left-2 top-2.5 h-4 w-4 text-muted-foreground">
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
            className="h-4 w-4"
          >
            <circle cx="11" cy="11" r="8"></circle>
            <path d="m21 21-4.3-4.3"></path>
          </svg>
        </div>
        <Input
          placeholder="Search elections..."
          className="pl-8"
          value={searchTerm}
          onChange={(e) => setSearchTerm(e.target.value)}
        />
      </div>

      <div className="space-y-4">
        <div className="inline-flex h-10 items-center justify-center rounded-md bg-muted p-1 text-muted-foreground">
          <button
            className={`inline-flex items-center justify-center whitespace-nowrap rounded-sm px-3 py-1.5 text-sm font-medium ring-offset-background transition-all focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:pointer-events-none disabled:opacity-50 ${
              activeTab === "ongoing"
                ? "bg-background text-foreground shadow-sm"
                : "hover:bg-background/50 hover:text-foreground"
            }`}
            onClick={() => setActiveTab("ongoing")}
          >
            Ongoing ({filteredOngoing.length})
          </button>
          <button
            className={`inline-flex items-center justify-center whitespace-nowrap rounded-sm px-3 py-1.5 text-sm font-medium ring-offset-background transition-all focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:pointer-events-none disabled:opacity-50 ${
              activeTab === "upcoming"
                ? "bg-background text-foreground shadow-sm"
                : "hover:bg-background/50 hover:text-foreground"
            }`}
            onClick={() => setActiveTab("upcoming")}
          >
            Upcoming ({filteredUpcoming.length})
          </button>
          <button
            className={`inline-flex items-center justify-center whitespace-nowrap rounded-sm px-3 py-1.5 text-sm font-medium ring-offset-background transition-all focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:pointer-events-none disabled:opacity-50 ${
              activeTab === "completed"
                ? "bg-background text-foreground shadow-sm"
                : "hover:bg-background/50 hover:text-foreground"
            }`}
            onClick={() => setActiveTab("completed")}
          >
            Completed ({filteredCompleted.length})
          </button>
        </div>

        {activeTab === "ongoing" && (
          <div className="space-y-4">
            {filteredOngoing.length > 0 ? (
              <div className="grid gap-4 md:grid-cols-2 lg:grid-cols-3">
                {filteredOngoing.map((election) => (
                  <Card key={election.id} className="transition-all hover:shadow-md">
                    <CardHeader>
                      <CardTitle>{election.title}</CardTitle>
                      <CardDescription>Ends on {new Date(election.endDate).toLocaleDateString()}</CardDescription>
                    </CardHeader>
                    <CardContent>
                      <div className="space-y-2">
                        <div className="flex justify-between">
                          <span>Candidates:</span>
                          <span>{election.candidates}</span>
                        </div>
                        <div className="flex justify-between">
                          <span>Total Votes:</span>
                          <span>{election.votes}</span>
                        </div>
                        <div className="flex justify-between">
                          <span>Your Status:</span>
                          <span className="text-green-500 font-medium">Voted</span>
                        </div>
                      </div>
                    </CardContent>
                    <CardFooter>
                      <Button className="w-full" onClick={() => openElectionDetails(election)}>
                        View Details
                      </Button>
                    </CardFooter>
                  </Card>
                ))}
              </div>
            ) : (
              <div className="text-center py-12">
                <p className="text-muted-foreground">No ongoing elections found matching your search.</p>
              </div>
            )}
          </div>
        )}

        {activeTab === "upcoming" && (
          <div className="space-y-4">
            {filteredUpcoming.length > 0 ? (
              <div className="grid gap-4 md:grid-cols-2 lg:grid-cols-3">
                {filteredUpcoming.map((election) => (
                  <Card key={election.id} className="transition-all hover:shadow-md">
                    <CardHeader>
                      <CardTitle>{election.title}</CardTitle>
                      <CardDescription>Starts on {new Date(election.startDate).toLocaleDateString()}</CardDescription>
                    </CardHeader>
                    <CardContent>
                      <div className="space-y-2">
                        <div className="flex justify-between">
                          <span>Candidates:</span>
                          <span>{election.candidates}</span>
                        </div>
                        <div className="flex justify-between">
                          <span>Registration:</span>
                          <span className="text-amber-500 font-medium">Open</span>
                        </div>
                      </div>
                    </CardContent>
                    <CardFooter className="flex flex-col space-y-2">
                      <Button className="w-full" onClick={() => openElectionDetails(election)}>
                        View Details
                      </Button>
                      <Button variant="outline" className="w-full">
                        Apply as Candidate
                      </Button>
                    </CardFooter>
                  </Card>
                ))}
              </div>
            ) : (
              <div className="text-center py-12">
                <p className="text-muted-foreground">No upcoming elections found matching your search.</p>
              </div>
            )}
          </div>
        )}

        {activeTab === "completed" && (
          <div className="space-y-4">
            {filteredCompleted.length > 0 ? (
              <div className="grid gap-4 md:grid-cols-2 lg:grid-cols-3">
                {filteredCompleted.map((election) => (
                  <Card key={election.id} className="transition-all hover:shadow-md">
                    <CardHeader>
                      <CardTitle>{election.title}</CardTitle>
                      <CardDescription>
                        Completed on {new Date(election.completedDate).toLocaleDateString()}
                      </CardDescription>
                    </CardHeader>
                    <CardContent>
                      <div className="space-y-2">
                        <div className="flex justify-between">
                          <span>Winner:</span>
                          <span className="font-medium">{election.winner}</span>
                        </div>
                        <div className="flex justify-between">
                          <span>Total Votes:</span>
                          <span>{election.votes}</span>
                        </div>
                        <div className="flex justify-between">
                          <span>Your Status:</span>
                          <span className="text-blue-500 font-medium">Participated</span>
                        </div>
                      </div>
                    </CardContent>
                    <CardFooter>
                      <Button className="w-full" onClick={() => openElectionDetails(election)}>
                        View Results
                      </Button>
                    </CardFooter>
                  </Card>
                ))}
              </div>
            ) : (
              <div className="text-center py-12">
                <p className="text-muted-foreground">No completed elections found matching your search.</p>
              </div>
            )}
          </div>
        )}
      </div>

      <ElectionDetailsModal isOpen={isModalOpen} onClose={closeElectionDetails} election={selectedElection} />
    </div>
  )
}

