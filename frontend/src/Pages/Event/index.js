import React, { useEffect, useState } from 'react'
import styles from './style.module.scss'
import { Link, useParams } from 'react-router-dom'
import Navbar from '../../Components/Navbar'
import { Helmet } from 'react-helmet'
import eventImg from '../../Assets/eventImg.jpg'
import NumberInput from '../../Components/NumberInput'
import axios from 'axios'


const EventPage = () => {
    const [event, setEvent] = useState(null);
    const [config, setConfig] = useState(null);
    const { id } = useParams();
    const userRole = localStorage.getItem('userRole');
    const userId = localStorage.getItem('userId');

    const fetchEventData = async () => {
        const apiUrl = `http://localhost:8080/api/events/${id}`;
        const response = await axios.get(apiUrl);
        console.log(response)
        setEvent(response.data);
    };

    const fetchConfigData = async () => {
        const apiUrl = `http://localhost:8080/api/configure`;
        const response = await axios.get(apiUrl);
        setConfig(response.data);
    };

    useEffect(() => {
        fetchEventData();
        fetchConfigData();
        console.log('event: ', event);
    }, []) // eslint-disable-next-line

    const [ticketQuantity, setTicketQuantity] = useState(1);

    const handleTicketQuantity = (quantity) => {
        setTicketQuantity(quantity);
    };

    const handleBuyTicket = async (eventId) => {
        try {
            const apiUrl = `http://localhost:8080/api/events/${eventId}/buy-ticket?customerId=${userId}&numberOfTicketsToBuy=${ticketQuantity}`; // Replace with your API endpoint
            const response = await axios.post(apiUrl);
            if (response.status === 500) {
                alert('Server error. Please try again later.');
            }
            alert(`${ticketQuantity} ticket(s) bought successfully.`);
            console.log(`${ticketQuantity} ticket(s) bought successfully.`, response.data);
        } catch (error) {
            alert('Error buying ticket:', error);
        }
    }

    const handleReleaseTicket = async (eventId) => {
        console.log(eventId)
        try {
            const apiUrl = `http://localhost:8080/api/events/${eventId}/start-ticketing`; // Replace with your API endpoint
            const response = await axios.post(apiUrl);

            if (response.status === 500) {
                return null;
            }

            alert('Ticket releasing process started.');
            console.log('Ticket releasing process started.', response.data);
        } catch (error) {
            alert('Error releasing ticket:', error);
        }
    };

    return (
        <div className={styles.container}>
            <Helmet><title>Event</title></Helmet>
            <Navbar />
            <div className={styles.eventHeader}>
                <img src={eventImg} alt="eventImg" />
                <h2>{event?.name}</h2>
            </div>
            <div className={styles.eventDetails}>
                <h2>Event Details</h2>
                <ul className={styles.eventDetailsInfo}>
                    <li><p>Name:</p> <span className={styles.eventTitle}>{event?.name}</span></li>
                    <li><p>Location:</p> <span>{event?.location}</span></li>
                    <li><p>Event Date:</p><span>{new Date(event?.eventDate).getDate()} {new Date(event?.eventDate).toLocaleString('default', { month: 'long' })} {new Date(event?.eventDate).getFullYear()}</span></li>
                    <li><p>Event Time:</p><span>{new Date(`1970-01-01T${event?.eventTime}Z`).toLocaleTimeString('en-US', { hour12: true, hour: '2-digit', minute: '2-digit' })}</span></li>
                    <li><p>Total Tickets:</p> <span>{event?.totalTickets}</span></li>
                    <li><p>Available Tickets:</p> <span>{event?.ticketPool.availableTicketCount}</span></li>
                </ul>
            </div>
            <div className={styles.additionalContent}>
                {userRole === 'customer' && (
                    <div className={styles.customerControls}>
                        <ul>
                            <li><p>Available Tickets:</p> <strong>{event?.ticketPool.availableTicketCount}</strong></li>
                            <li><p>Ticket Quantity:</p><NumberInput min={1} max={Math.min(config?.customerRetrievalRate, event?.ticketPool.availableTicketCount)} onChange={handleTicketQuantity} /></li>
                            <li><button className={styles.buyTicketButton} onClick={() => handleBuyTicket(event?.id, ticketQuantity)}>Buy Ticket</button></li>
                        </ul>
                    </div>
                )}
                {userRole === 'vendor' && event?.vendorId === localStorage.getItem('userId') && (
                    <div className={styles.vendorControls}>
                        {/* <p>Vendor can see this</p> */}
                        <button onClick={() => handleReleaseTicket(event?.id)}>Release Tickets</button>
                    </div>
                )}
                {userRole === "null" && (
                    <p>You need to sign in to access more controls. <Link to="/profile">Go sign in</Link></p>
                )}
            </div>

        </div>
    )
}

export default EventPage