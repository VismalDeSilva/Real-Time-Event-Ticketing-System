import React, { useContext, useEffect, useState } from 'react'
import styles from './style.module.scss'
import { Helmet } from 'react-helmet'
import Navbar from '../Navbar'
import { Link, useParams } from 'react-router-dom'

import profileImg from '../../Assets/profileImg.png'
import { UserContext } from '../../Pages/Auth/UserContext'

const CustomerProfile = () => {
    const [customerInfo, setCustomerInfo] = useState(null);
    const { logout, userId } = useContext(UserContext);

    const handleLogout = () => {
        logout();
        window.location.href = '/'; // redirect to homepage after logout
    };

    const fetchCustomerInfo = async () => {
        const apiUrl = `http://localhost:8080/api/customers/id/${userId}`;
        try {
            const response = await fetch(apiUrl);
            if (response.ok) {
                const data = await response.json();
                setCustomerInfo(data);
                console.log("customerInfo", customerInfo);
            } else {
                console.error('Error fetching customer data:', response.status);
            }
        } catch (error) {
            console.error('Error fetching customer data:', error);
        }
    };

    useEffect(() => {
        fetchCustomerInfo();
        console.log(customerInfo);
    }, []);


    return (
        <div className={styles.container}>
            <Helmet><title>Profile</title></Helmet>
            <Navbar />
            <div className={styles.customerContainer}>
                <h1>Customer Profile Page</h1>
                <img src={profileImg} alt="profileImg" />
                <ul>
                    <li><p>Name :</p><span>{customerInfo?.name}</span></li>
                    <li><p>Email :</p><span>{customerInfo?.email}</span></li>
                    <li><p>Phone Number :</p><span>{customerInfo?.phoneNumber}</span></li>
                </ul>
                <div className={styles.ticketsContainer}>
                    <h3>Tickets Bought</h3>
                    {customerInfo?.tickets.length > 0 ? (
                        customerInfo.tickets.map((ticket) => (
                            <Link to={`/event/${ticket.eventId}`} className={styles.ticket} key={ticket._id}>
                                <p>{ticket.eventName}</p>
                                <p>${ticket.price}</p>
                            </Link>
                        ))
                    ) : (
                        <p>No tickets bought</p>
                    )}
                </div>
                <div className={styles.buttonContainer}>
                    <button className={styles.logoutButton} onClick={handleLogout}>Logout</button>
                </div>
            </div>
        </div>
    )
}

export default CustomerProfile