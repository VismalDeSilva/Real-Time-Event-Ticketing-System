import React from 'react'
import { Helmet } from 'react-helmet'
import Navbar from '../../Components/Navbar'
import EventsComponent from '../../Components/EventsComponent'

const index = () => {
  return (
    <div>
        <Helmet><title>Events</title></Helmet>
        <Navbar />
        <EventsComponent />
    </div>
  )
}

export default index