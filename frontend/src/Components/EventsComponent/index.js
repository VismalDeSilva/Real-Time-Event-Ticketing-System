import React, { useState, useEffect } from 'react'
import styles from './style.module.scss'
import axios from 'axios'

import eventImg from '../../Assets/eventImg.jpg'
import { Link } from 'react-router-dom'

const dateFormatter = (date) => {
    const eventDate = new Date(date);
    return eventDate.toLocaleString('default', {
        day: 'numeric',
        month: 'long',
        year: 'numeric'
    });
}

const EventsComponent = () => {
    const [events, setEvents] = useState([]);
    const fetchEvents = async () => {
        const apiUrl = 'http://localhost:8080/api/events/all';
        const response = await axios.get(apiUrl);
        setEvents(response.data);
    };
    useEffect(() => {
        fetchEvents();
    }, [])
    return (
        <div className={styles.container}>
            <h2>Latest Events</h2>
            <div className={styles.eventContainer}>
                {events.length === 0 ? (
                    <p>No events to show.</p>
                ) : (
                    events.map((event) => {
                        return (
                            <div className={styles.event}>
                                <img src={eventImg} alt="eventImg" />
                                <h3>{event.name}</h3>
                                <p>{dateFormatter(event.eventDate)}</p>
                                <p>{event.location}</p>
                                <Link to={`/event/${event.id}`}><button>View Event</button></Link>
                            </div>
                        )
                    })
                )}
            </div>
        </div>
    )
}

export default EventsComponent