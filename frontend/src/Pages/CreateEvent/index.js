import React from 'react'
import styles from './style.module.scss'
import { Helmet } from 'react-helmet'
import Navbar from '../../Components/Navbar'
import axios from 'axios'

const index = () => {

    const handleCreateEventData = async (e) => {
        e.preventDefault();
        const data = {
            name: e.target.name.value,
            location: e.target.location.value,
            eventDate: e.target.eventDate.value,
            eventTime: e.target.eventTime.value,
            vendorId: localStorage.getItem('userId'),
            totalTickets: Number(e.target.totalTickets.value)
        };
        // data.vendorId = localStorage.getItem('vendorId');
        const apiUrl = 'http://localhost:8080/api/events/create';
        try {
            console.log(data)
            const response = await axios.post(apiUrl, data);
            if (response.status === 201) {
                window.location.href = '/events';
            } else if (response.status === 500) {
                alert('Server error. Please try again later.');
            }
        } catch (error) {
            console.error('Error creating event:', error);
        }
    }

    return (
        <div className={styles.container}>
            <Helmet><title>Create Event</title></Helmet>
            <Navbar />
            <div className={styles.createEventcontainer}>
                <h2>Create Event</h2>
                <form className={styles.eventInfoForm} onSubmit={handleCreateEventData}>
                    <input type="text" name="name" placeholder='Event Name' className={styles.eventName} />
                    <label>
                        <p>Event Location:</p>
                        <input type="text" name="location" />
                    </label>
                    <label>
                        <p>Event Date:</p>
                        <input type="date" name="eventDate" />
                    </label>
                    <label>
                        <p>Event Time:</p>
                        <input type="time" name="eventTime" />
                    </label>
                    <label>
                        <p>Total Tickets:</p>
                        <input type="number" name="totalTickets" />
                    </label>
                    <button type="submit">Create Event</button>
                </form>
            </div>
        </div>
    )
}

export default index