"use client"

import React from "react"

import { useState } from "react"

export function Tabs({ defaultValue, className = "", children }) {
  const [activeTab, setActiveTab] = useState(defaultValue)

  // Filter children to get TabsList and TabsContent
  const tabsList = children.find((child) => child.type === TabsList)
  const tabsContent = children.filter((child) => child.type === TabsContent)

  // Clone TabsList with the activeTab state
  const clonedTabsList = React.cloneElement(tabsList, {
    activeTab,
    setActiveTab,
  })

  // Filter and render only the active TabsContent
  const activeContent = tabsContent.find((content) => content.props.value === activeTab)

  return (
    <div className={`space-y-4 ${className}`}>
      {clonedTabsList}
      {activeContent}
    </div>
  )
}

export function TabsList({ activeTab, setActiveTab, children, className = "" }) {
  // Clone children (TabsTrigger) with activeTab state
  const clonedChildren = React.Children.map(children, (child) => {
    if (React.isValidElement(child)) {
      return React.cloneElement(child, {
        isActive: activeTab === child.props.value,
        onClick: () => setActiveTab(child.props.value),
      })
    }
    return child
  })

  return (
    <div
      className={`inline-flex h-10 items-center justify-center rounded-md bg-muted p-1 text-muted-foreground ${className}`}
    >
      {clonedChildren}
    </div>
  )
}

export function TabsTrigger({ value, isActive, onClick, children, className = "" }) {
  return (
    <button
      className={`inline-flex items-center justify-center whitespace-nowrap rounded-sm px-3 py-1.5 text-sm font-medium ring-offset-background transition-all focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:pointer-events-none disabled:opacity-50 ${
        isActive ? "bg-background text-foreground shadow-sm" : "hover:bg-background/50 hover:text-foreground"
      } ${className}`}
      onClick={onClick}
    >
      {children}
    </button>
  )
}

export function TabsContent({ value, children, className = "" }) {
  return (
    <div
      className={`mt-2 ring-offset-background focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 ${className}`}
    >
      {children}
    </div>
  )
}

