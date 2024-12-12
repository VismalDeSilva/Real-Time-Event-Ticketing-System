import axios from 'axios';
import React from 'react'

// const index = () => {
//   return (
//     <div>index</div>
//   )
// }

const handleReleaseTicket = (eventId) => {
    console.log(eventId)
    axios.post(`http://localhost:8080/api/events/${eventId}/start-ticketing`)
        .then(response => {
            if (response.status === 500) {
                return null;
            }
            alert('Ticket releasing process started.');
            console.log('Ticket releasing process started.', response.data);
        })
        .catch(error => {
            console.error('Error releasing ticket:', error);
        });
};


export default handleReleaseTicket